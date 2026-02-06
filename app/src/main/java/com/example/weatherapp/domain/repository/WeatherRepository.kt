package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.models.entity.RootForecastResponseEntity
import com.example.weatherapp.domain.models.entity.SearchResponseEntity
import com.example.weatherapp.domain.result.AppResult

interface WeatherRepository {

    suspend fun getSearchLocation(query: String): AppResult<List<SearchResponseEntity>>
    suspend fun getForecast(query: String): AppResult<RootForecastResponseEntity>
}