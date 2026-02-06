package com.example.weatherapp.domain.models.entity

data class RootForecastResponseEntity(
    val location: LocationResponseEntity,
    val current: CurrentResponseEntity,
    val forecast: ForecastResponseEntity,
)
