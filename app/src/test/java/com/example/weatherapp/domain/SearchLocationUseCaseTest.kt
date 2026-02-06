package com.example.weatherapp.domain

import com.example.weatherapp.builder.SearchResponseTestBuilder
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.result.AppError
import com.example.weatherapp.domain.result.AppResult
import com.example.weatherapp.domain.usecase.SearchLocationUseCase
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
class SearchLocationUseCaseTest {

    private val repository: WeatherRepository = mockk()
    private lateinit var useCase: SearchLocationUseCase

    @Before
    fun setup() {
        useCase = SearchLocationUseCase(repository)
    }

    @Test
    fun `invoke returns Success when repository returns Success`() = runTest {
        val query = "Bo"
        val list = listOfNotNull(SearchResponseTestBuilder().build())

        val expected = AppResult.Success(list)

        coEvery { repository.getSearchLocation(query) } returns expected

        val result = useCase(query)

        assertTrue(result is AppResult.Success)
        assertEquals(expected, result)
        coVerify(exactly = 1) { repository.getSearchLocation(query) }
    }

    @Test
    fun `invoke returns Error when repository returns Error`() = runTest {
        val query = "Bo"
        val expected = AppResult.Error(AppError.Network)

        coEvery { repository.getSearchLocation(query) } returns expected

        val result = useCase(query)

        assertTrue(result is AppResult.Error)
        assertEquals(expected, result)
        coVerify(exactly = 1) { repository.getSearchLocation(query) }
    }
}
