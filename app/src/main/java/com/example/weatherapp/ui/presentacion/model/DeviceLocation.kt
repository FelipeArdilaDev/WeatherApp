package com.example.weatherapp.ui.presentacion.model

data class DeviceLocation(
    val latitude: Double,
    val longitude: Double,
    val locality: String?,
    val adminArea: String?
)
