package com.example.weatherapp.data.repository

import com.example.weatherapp.builder.RootForecastResponseTestBuilder
import com.example.weatherapp.builder.SearchResponseTestBuilder
import com.example.weatherapp.data.network.datasource.WeatherDataSource
import com.example.weatherapp.domain.result.AppError
import com.example.weatherapp.domain.result.AppResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryImplTest {


    private val dataSource: WeatherDataSource = mockk()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var repo: WeatherRepositoryImpl

    @Before
    fun setup() {
        repo = WeatherRepositoryImpl(
            weatherDataSource = dataSource,
            io = dispatcher
        )
    }

    @Test
    fun `getSearchLocation success`() = runTest(dispatcher) {
        val query = "Bogota"
        val expected = listOf(
            SearchResponseTestBuilder().build()
        )


        coEvery { dataSource.searchLocation(query) } returns AppResult.Success(expected)

        val result = repo.getSearchLocation(query)

        assertTrue(result is AppResult.Success)
        assertEquals(expected, (result as AppResult.Success).data)

        coVerify(exactly = 1) { dataSource.searchLocation(query) }
    }

    @Test
    fun `getSearchLocation error returns Error`() = runTest(dispatcher) {
        val query = "Bogota"
        val expectedError = AppError.Network

        coEvery { dataSource.searchLocation(query) } returns AppResult.Error(expectedError)

        val result = repo.getSearchLocation(query)

        assertTrue(result is AppResult.Error)
        assertEquals(expectedError, (result as AppResult.Error).error)

        coVerify(exactly = 1) { dataSource.searchLocation(query) }
    }

    @Test
    fun `getSearchLocation http error returns Error Http`() = runTest(dispatcher) {
        val query = "Bogota"
        val expectedError = AppError.Http(code = 404, message = "Not Found")

        coEvery { dataSource.searchLocation(query) } returns AppResult.Error(expectedError)

        val result = repo.getSearchLocation(query)

        assertTrue(result is AppResult.Error)
        assertEquals(expectedError, (result as AppResult.Error).error)

        coVerify(exactly = 1) { dataSource.searchLocation(query) }
    }

    @Test
    fun `getForecast success returns Success`() = runTest(dispatcher) {
        val query = "Bogota"

        // Si tienes builder, úsalo aquí. Si no, puedes mockear con mockk(relaxed = true)
        val forecast = RootForecastResponseTestBuilder().build()

        coEvery { dataSource.getForecast(query) } returns AppResult.Success(forecast)

        val result = repo.getForecast(query)

        assertTrue(result is AppResult.Success)
        assertEquals(forecast, (result as AppResult.Success).data)

        coVerify(exactly = 1) { dataSource.getForecast(query) }
    }

    @Test
    fun `getForecast error returns Error`() = runTest(dispatcher) {
        val query = "Bogota"
        val expectedError = AppError.Unknown(message = "boom")

        coEvery { dataSource.getForecast(query) } returns AppResult.Error(expectedError)

        val result = repo.getForecast(query)

        assertTrue(result is AppResult.Error)
        assertEquals(expectedError, (result as AppResult.Error).error)

        coVerify(exactly = 1) { dataSource.getForecast(query) }
    }

    @Test
    fun `getForecast http error returns Error Http`() = runTest(dispatcher) {
        val query = "Bogota"
        val expectedError = AppError.Http(code = 500, message = "Server error")

        coEvery { dataSource.getForecast(query) } returns AppResult.Error(expectedError)

        val result = repo.getForecast(query)

        assertTrue(result is AppResult.Error)
        assertEquals(expectedError, (result as AppResult.Error).error)

        coVerify(exactly = 1) { dataSource.getForecast(query) }
    }
}
