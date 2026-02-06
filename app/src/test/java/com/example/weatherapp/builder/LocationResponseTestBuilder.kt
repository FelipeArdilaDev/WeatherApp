package com.example.weatherapp.builder

import com.example.weatherapp.data.network.models.response.dto.LocationResponseDto
import com.example.weatherapp.domain.models.entity.LocationResponseEntity

data class LocationResponseTestBuilder(
    val name: String = "Bogota",
    val region: String = "Cundinamarca",
    val country: String = "Colombia",
    val lat: Double = 4.611,
    val lon: Double = -74.08175,
    val tzId: String = "America/Bogota",
    val localtimeEpoch: Double = 1636121234.0,
    val localtime: String = "2021-11-04 10:00"
) {
    fun build() =
        LocationResponseEntity(name, region, country, lat, lon, tzId, localtimeEpoch, localtime)

    fun buildToDto() =
        LocationResponseDto(name, region, country, lat, lon, tzId, localtimeEpoch, localtime)
}