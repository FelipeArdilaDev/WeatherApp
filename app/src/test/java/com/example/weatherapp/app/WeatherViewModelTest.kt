package com.example.weatherapp.app

import app.cash.turbine.test
import com.example.weatherapp.data.location.LocationProvider
import com.example.weatherapp.domain.models.entity.RootForecastResponseEntity
import com.example.weatherapp.domain.models.entity.SearchResponseEntity
import com.example.weatherapp.domain.result.AppError
import com.example.weatherapp.domain.result.AppResult
import com.example.weatherapp.domain.usecase.GetForecastUseCase
import com.example.weatherapp.domain.usecase.SearchLocationUseCase
import com.example.weatherapp.ui.presentacion.WeatherViewModel
import com.example.weatherapp.ui.presentacion.model.DeviceLocation
import com.example.weatherapp.ui.presentacion.state.WeatherEffect
import com.example.weatherapp.ui.presentacion.state.WeatherEvent
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private val searchLocationUseCase: SearchLocationUseCase = mockk()
    private val getForecastUseCase: GetForecastUseCase = mockk()
    private val locationProvider: LocationProvider = mockk()

    private val dispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(dispatcher)

    private lateinit var vm: WeatherViewModel

    @Before
    fun setup() {
        vm = WeatherViewModel(
            searchLocationUseCase = searchLocationUseCase,
            getForecastUseCase = getForecastUseCase,
            locationProvider = locationProvider,
            ioDispatcher = dispatcher
        )
    }

    @Test
    fun `ScreenShown emits RequestLocationPermissions`() = runTest(dispatcher) {
        vm.effect.test {
            vm.onEvent(WeatherEvent.ScreenShown)
            assertEquals(WeatherEffect.RequestLocationPermissions, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Retry emits RequestLocationPermissions`() = runTest(dispatcher) {
        vm.effect.test {
            vm.onEvent(WeatherEvent.Retry)
            assertEquals(WeatherEffect.RequestLocationPermissions, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `LocationPermissionDenied updates state and emits snackbar`() = runTest(dispatcher) {
        vm.effect.test {
            vm.onEvent(WeatherEvent.LocationPermissionDenied)
            advanceUntilIdle()

            val s = vm.state.value
            assertFalse(s.isSearching)
            assertFalse(s.isForecastLoading)
            assertTrue(s.errorMessage?.contains("denegado", ignoreCase = true) == true)

            assertEquals(
                WeatherEffect.ShowSnackbar("Permiso de ubicación denegado"),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `LoadFromDeviceLocation success loads forecast`() = runTest(dispatcher) {
        coEvery { locationProvider.getLastKnownLocation() } returns (DeviceLocation(1.0, 2.0,"",""))

        val forecast = mockk<RootForecastResponseEntity>(relaxed = true)
        coEvery { getForecastUseCase.invoke("1.0,2.0") } returns AppResult.Success(forecast)

        vm.onEvent(WeatherEvent.LoadFromDeviceLocation)
        advanceUntilIdle()

        val s = vm.state.value
        assertFalse(s.isForecastLoading)
        assertEquals(forecast, s.forecast)
        assertNull(s.errorMessage)
        assertFalse(s.showDropdown)

        coVerify(exactly = 1) { locationProvider.getLastKnownLocation() }
        coVerify(exactly = 1) { getForecastUseCase.invoke("1.0,2.0") }
    }

    @Test
    fun `LoadFromDeviceLocation failure sets error and emits snackbar`() = runTest(dispatcher) {
        coEvery { locationProvider.getLastKnownLocation() } throws RuntimeException("no location")

        vm.effect.test {
            vm.onEvent(WeatherEvent.LoadFromDeviceLocation)
            advanceUntilIdle()

            val s = vm.state.value
            assertFalse(s.isForecastLoading)
            assertNull(s.forecast)
            assertNotNull(s.errorMessage)

            assertEquals(
                WeatherEffect.ShowSnackbar("No se pudo obtener ubicación"),
                awaitItem()
            )
            coVerify(exactly = 1) { locationProvider.getLastKnownLocation() }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `LocationSelected success clears query and loads forecast`() = runTest(dispatcher) {
        val name = "Bogota"
        val forecast = mockk<RootForecastResponseEntity>(relaxed = true)

        coEvery { getForecastUseCase.invoke(name) } returns AppResult.Success(forecast)

        vm.onEvent(WeatherEvent.LocationSelected(name))
        advanceUntilIdle()

        val s = vm.state.value
        assertEquals("", s.query)
        assertFalse(s.isForecastLoading)
        assertEquals(forecast, s.forecast)
        assertFalse(s.showDropdown)
        assertTrue(s.searchResults.isEmpty())

        coVerify(exactly = 1) { getForecastUseCase.invoke(name) }
    }

    @Test
    fun `LocationSelected error emits snackbar and sets error`() = runTest(dispatcher) {
        val name = "Bogota"
        coEvery { getForecastUseCase.invoke(name) } returns AppResult.Error(AppError.Http(500, "Server error"))

        vm.effect.test {
            vm.onEvent(WeatherEvent.LocationSelected(name))
            advanceUntilIdle()

            val s = vm.state.value
            assertFalse(s.isForecastLoading)
            assertNull(s.forecast)
            assertNotNull(s.errorMessage)

            assertEquals(
                WeatherEffect.ShowSnackbar("Error cargando pronóstico"),
                awaitItem()
            )
            coVerify(exactly = 1) { getForecastUseCase.invoke(name) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `QueryChanged uses flatMapLatest - new query cancels previous search result`() = runTest(dispatcher) {
        val q1 = "Bog"
        val q2 = "Bogot"

        val r1 = listOf(mockk<SearchResponseEntity>(relaxed = true))
        val r2 = listOf(mockk<SearchResponseEntity>(relaxed = true), mockk(relaxed = true))

        // Simula que la primera búsqueda "tarda" más (en tests lo modelamos con respuestas distintas)
        coEvery { searchLocationUseCase.invoke(q1) } returns AppResult.Success(r1)
        coEvery { searchLocationUseCase.invoke(q2) } returns AppResult.Success(r2)

        // Tecleo rápido: q1 y antes de debounce completo, q2
        vm.onEvent(WeatherEvent.QueryChanged(q1))
        advanceTimeBy(200)

        vm.onEvent(WeatherEvent.QueryChanged(q2))
        // termina debounce para q2
        advanceTimeBy(400)
        advanceUntilIdle()

        val s = vm.state.value
        assertEquals(q2, s.query)
        assertEquals(r2, s.searchResults)

        // Debe haber buscado SOLO q2 (porque q1 no alcanzó el debounce)
        coVerify(exactly = 0) { searchLocationUseCase.invoke(q1) }
        coVerify(exactly = 1) { searchLocationUseCase.invoke(q2) }
    }
}
