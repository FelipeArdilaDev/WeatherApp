package com.example.weatherapp.domain

import com.example.weatherapp.domain.usecase.GetForecastUseCase
import com.example.weatherapp.builder.RootForecastResponseTestBuilder
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.result.AppError
import com.example.weatherapp.domain.result.AppResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetForecastUseCaseTest {

    private val repository: WeatherRepository = mockk()
    private lateinit var useCase: GetForecastUseCase

    @Before
    fun setup() {
        useCase = GetForecastUseCase(repository)
    }

    @Test
    fun `invoke returns Success when repository returns Success`() = runTest {
        val query = "Bogota"
        val forecast = RootForecastResponseTestBuilder().build()

        val expected = AppResult.Success(forecast)

        coEvery { repository.getForecast(query) } returns expected

        val result = useCase(query)

        assertTrue(result is AppResult.Success)
        assertEquals(expected, result)
        coVerify(exactly = 1) { repository.getForecast(query) }
    }

    @Test
    fun `invoke returns Error when repository returns Error`() = runTest {
        val query = "Bogota"
        val expected = AppResult.Error(AppError.Http(code = 404, message = "Not found"))

        coEvery { repository.getForecast(query) } returns expected

        val result = useCase(query)

        assertTrue(result is AppResult.Error)
        assertEquals(expected, result)
        coVerify(exactly = 1) { repository.getForecast(query) }
    }
}
