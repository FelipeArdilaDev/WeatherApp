package com.example.weatherapp.builder

import com.example.weatherapp.data.network.models.response.dto.SearchResponseDto
import com.example.weatherapp.domain.models.entity.SearchResponseEntity
import kotlin.Int

data class SearchResponseTestBuilder(
    val id: Int = 0,
    val name: String = "",
    val region: String = "",
    val country: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0
) {
    fun build() = SearchResponseEntity(
        id,
        name,
        region,
        country,
        lat,
        lon
    )

    fun buildToDto() = SearchResponseDto(
        id,
        name,
        region,
        country,
        lat,
        lon
    )
}