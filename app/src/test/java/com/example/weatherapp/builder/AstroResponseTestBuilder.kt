package com.example.weatherapp.builder

import com.example.weatherapp.domain.models.entity.AstroResponseEntity
import kotlin.String

data class AstroResponseTestBuilder(
    val sunrise: String = "",
    val sunset: String = "",
    val moonrise: String = "",
    val moonSet: String = "",
    val moonPhase: String = "",
    val moonIllumination: Double = 0.0,
    val isMoonUp: Double = 0.0,
    val isSunUp: Double = 0.0
) {
    fun build() = AstroResponseEntity(
        sunrise,
        sunset,
        moonrise,
        moonSet,
        moonPhase,
        moonIllumination,
        isMoonUp,
        isSunUp
    )
}
