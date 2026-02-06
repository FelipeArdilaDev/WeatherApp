package com.example.weatherapp.domain.models.entity

data class ForecastResponseEntity(
    val forecastDay: List<ForecastDayResponseEntity>,
)
