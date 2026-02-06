package com.example.weatherapp.domain.models.entity

data class DayResponseEntity(
    val maxTempC: Double,
    val maxTempF: Double,
    val minTempC: Double,
    val minTempF: Double,
    val avgTempC: Double,
    val avgTempF: Double,
    val maxWindMph: Double,
    val maxWindKph: Double,
    val totalPrecipMm: Double,
    val totalPrecipIn: Double,
    val totalSnowCm: Double,
    val avgVisKm: Double,
    val avgVisMiles: Double,
    val avgHumidity: Double,
    val dailyWillItRain: Double,
    val dailyChanceOfRain: Double,
    val dailyWillItSnow: Double,
    val dailyChanceOfSnow: Double,
    val condition: ConditionResponseEntity,
    val uv: Double,
)
