package com.example.weatherapp.data.network.models.response.dto

import com.example.weatherapp.domain.models.entity.SearchResponseEntity
import kotlin.Int

data class SearchResponseDto(
    val id: Int,
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double
)

fun SearchResponseDto.mapToDomain() = SearchResponseEntity(
    id = id,
    name = name,
    region = region,
    country = country,
    lat = lat,
    lon = lon
)
