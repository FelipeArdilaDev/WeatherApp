package com.example.weatherapp.data.network.datasource

import com.example.weatherapp.data.network.api.WeatherApi
import com.example.weatherapp.data.network.models.response.dto.mapToDomain
import com.example.weatherapp.data.network.utils.safeApiCall
import com.example.weatherapp.domain.models.entity.RootForecastResponseEntity
import com.example.weatherapp.domain.models.entity.SearchResponseEntity
import com.example.weatherapp.domain.result.AppResult
import javax.inject.Inject


class WeatherDataSourceImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherDataSource {

    override suspend fun searchLocation(
        query: String
    ): AppResult<List<SearchResponseEntity>> =
        safeApiCall(
            call = { api.searchLocation(query) },
            mapper = { dtoList -> dtoList.map { it.mapToDomain() } }
        )

    override suspend fun getForecast(
        query: String
    ): AppResult<RootForecastResponseEntity> =
        safeApiCall(
            call = { api.getForecast(query, days = 3) },
            mapper = { it.mapToDomain() }
        )
}
