package com.example.weatherapp.domain.models.entity

data class LocationResponseEntity(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tzId: String,
    val localtimeEpoch: Double,
    val localtime: String,
)
