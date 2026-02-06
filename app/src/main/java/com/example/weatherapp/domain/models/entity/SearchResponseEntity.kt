package com.example.weatherapp.domain.models.entity

data class SearchResponseEntity(
    val id: Int,
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double
)
