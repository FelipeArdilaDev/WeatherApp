package com.example.weatherapp.domain.models.entity

data class AstroResponseEntity(
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonSet: String,
    val moonPhase: String,
    val moonIllumination: Double,
    val isMoonUp: Double,
    val isSunUp: Double,
)
