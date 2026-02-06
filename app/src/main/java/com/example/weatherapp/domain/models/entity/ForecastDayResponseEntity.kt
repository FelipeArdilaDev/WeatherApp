package com.example.weatherapp.domain.models.entity

data class ForecastDayResponseEntity(
    val date: String,
    val dateEpoch: Double,
    val day: DayResponseEntity,
    val astro: AstroResponseEntity,
    val hour: List<HourResponseEntity>,
)
