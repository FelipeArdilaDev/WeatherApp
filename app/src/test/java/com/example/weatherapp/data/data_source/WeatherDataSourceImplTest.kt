package com.example.weatherapp.data.data_source

import com.example.weatherapp.builder.RootForecastResponseTestBuilder
import com.example.weatherapp.builder.SearchResponseTestBuilder
import com.example.weatherapp.data.network.api.WeatherApi
import com.example.weatherapp.data.network.datasource.WeatherDataSourceImpl
import com.example.weatherapp.domain.result.AppError
import com.example.weatherapp.domain.result.AppResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherDataSourceImplTest {

    private val api: WeatherApi = mockk()
    private lateinit var dataSource: WeatherDataSourceImpl

    @Before
    fun setup() {
        dataSource = WeatherDataSourceImpl(api)
    }

    // -------------------------
    // searchLocation
    // -------------------------

    @Test
    fun `searchLocation - success 200 maps dto to domain and returns Success`() = runTest {
        val query = "Bogota"
        val dtoList = listOf(
            SearchResponseTestBuilder().buildToDto()
        )

        coEvery { api.searchLocation(query) } returns Response.success(dtoList)

        val result = dataSource.searchLocation(query)

        assertTrue(result is AppResult.Success)
        val data = (result as AppResult.Success).data
        assertEquals(dtoList.size, data.size)

        coVerify(exactly = 1) { api.searchLocation(query) }
    }

    @Test
    fun `searchLocation - http error (404) returns AppError_Http`() = runTest {
        val query = "Bogota"

        val errorBody = """{"error":"Not Found"}"""
            .toResponseBody("application/json".toMediaType())

        coEvery { api.searchLocation(query) } returns Response.error(404, errorBody)

        val result = dataSource.searchLocation(query)

        assertTrue(result is AppResult.Error)
        val err = (result as AppResult.Error).error

        assertTrue(err is AppError.Http)
        err as AppError.Http
        assertEquals(404, err.code)

        coVerify(exactly = 1) { api.searchLocation(query) }
    }

    @Test
    fun `searchLocation - network exception returns AppError_Network`() = runTest {
        val query = "Bogota"

        coEvery { api.searchLocation(query) } throws IOException("No internet")

        val result = dataSource.searchLocation(query)

        assertTrue(result is AppResult.Error)
        assertEquals(AppError.Network, (result as AppResult.Error).error)

        coVerify(exactly = 1) { api.searchLocation(query) }
    }

    // -------------------------
    // getForecast
    // -------------------------

    @Test
    fun `getForecast - success 200 maps dto to domain and returns Success`() = runTest {
        val query = "Bogota"
        val dto = RootForecastResponseTestBuilder().buildToDto()

        coEvery { api.getForecast(query, days = 3) } returns Response.success(dto)

        val result = dataSource.getForecast(query)

        assertTrue(result is AppResult.Success)

        coVerify(exactly = 1) { api.getForecast(query, days = 3) }
    }

    @Test
    fun `getForecast - http error (500) returns AppError_Http`() = runTest {
        val query = "Bogota"

        val errorBody = """{"error":"Server error"}"""
            .toResponseBody("application/json".toMediaType())

        coEvery { api.getForecast(query, days = 3) } returns Response.error(500, errorBody)

        val result = dataSource.getForecast(query)

        assertTrue(result is AppResult.Error)
        val err = (result as AppResult.Error).error

        assertTrue(err is AppError.Http)
        err as AppError.Http
        assertEquals(500, err.code)

        coVerify(exactly = 1) { api.getForecast(query, days = 3) }
    }

    @Test
    fun `getForecast - network exception returns AppError_Network`() = runTest {
        val query = "Bogota"

        coEvery { api.getForecast(query, days = 3) } throws IOException("timeout")

        val result = dataSource.getForecast(query)

        assertTrue(result is AppResult.Error)
        assertEquals(AppError.Network, (result as AppResult.Error).error)

        coVerify(exactly = 1) { api.getForecast(query, days = 3) }
    }
}
