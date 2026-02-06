package com.example.weatherapp.data.repository

import com.example.weatherapp.data.di.qualifers.IoDispatcher
import com.example.weatherapp.data.network.datasource.WeatherDataSource
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherDataSource: WeatherDataSource,
    @IoDispatcher private val io: CoroutineDispatcher
) : WeatherRepository {

    override suspend fun getSearchLocation(query: String) = withContext(io) {
        weatherDataSource.searchLocation(query)
    }

    override suspend fun getForecast(query: String) = withContext(io) {
        weatherDataSource.getForecast(query)
    }
}
