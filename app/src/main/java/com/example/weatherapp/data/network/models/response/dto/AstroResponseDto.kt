package com.example.weatherapp.data.network.models.response.dto

import com.example.weatherapp.domain.models.entity.AstroResponseEntity
import com.google.gson.annotations.SerializedName

data class AstroResponseDto(
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    @SerializedName("moonset")
    val moonSet: String,
    @SerializedName("moon_phase")
    val moonPhase: String,
    @SerializedName("moon_illumination")
    val moonIllumination: Double,
    @SerializedName("is_moon_up")
    val isMoonUp: Double,
    @SerializedName("is_sun_up")
    val isSunUp: Double
)

fun AstroResponseDto.mapToDomain() = AstroResponseEntity(
    sunrise = sunrise,
    sunset = sunset,
    moonrise = moonrise,
    moonSet = moonSet,
    moonPhase = moonPhase,
    moonIllumination = moonIllumination,
    isMoonUp = isMoonUp,
    isSunUp = isSunUp
)
