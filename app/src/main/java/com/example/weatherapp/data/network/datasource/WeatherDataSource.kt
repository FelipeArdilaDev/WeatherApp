package com.example.weatherapp.data.network.datasource

import com.example.weatherapp.domain.models.entity.RootForecastResponseEntity
import com.example.weatherapp.domain.models.entity.SearchResponseEntity
import com.example.weatherapp.domain.result.AppResult

interface WeatherDataSource {
    suspend fun searchLocation(query: String): AppResult<List<SearchResponseEntity>>
    suspend fun getForecast(query: String): AppResult<RootForecastResponseEntity>
}