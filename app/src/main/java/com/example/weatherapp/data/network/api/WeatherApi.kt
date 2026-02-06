package com.example.weatherapp.data.network.api

import com.example.weatherapp.data.network.models.response.dto.RootForecastResponseDto
import com.example.weatherapp.data.network.models.response.dto.SearchResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("search.json")
    suspend fun searchLocation(
        @Query("q") query: String
    ): Response<List<SearchResponseDto>>

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") location: String,
        @Query("days") days: Int = 3
    ): Response<RootForecastResponseDto>
}