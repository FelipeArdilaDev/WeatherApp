package com.example.weatherapp.builder

import com.example.weatherapp.domain.models.entity.ConditionResponseEntity
import com.example.weatherapp.domain.models.entity.DayResponseEntity
import kotlin.Double

data class DayResponseTestBuilder(
    val maxTempC: Double = 0.0,
    val maxTempF: Double = 0.0,
    val minTempC: Double = 0.0,
    val minTempF: Double = 0.0,
    val avgTempC: Double = 0.0,
    val avgTempF: Double = 0.0,
    val maxWindMph: Double = 0.0,
    val maxWindKph: Double = 0.0,
    val totalPrecipMm: Double = 0.0,
    val totalPrecipIn: Double = 0.0,
    val totalSnowCm: Double = 0.0,
    val avgVisKm: Double = 0.0,
    val avgVisMiles: Double = 0.0,
    val avgHumidity: Double = 0.0,
    val dailyWillItRain: Double = 0.0,
    val dailyChanceOfRain: Double = 0.0,
    val dailyWillItSnow: Double = 0.0,
    val dailyChanceOfSnow: Double = 0.0,
    val condition: ConditionResponseEntity = ConditionResponseTestBuilder().build(),
    val uv: Double = 0.0,
) {
    fun build() = DayResponseEntity(
        maxTempC,
        maxTempF,
        minTempC,
        minTempF,
        avgTempC,
        avgTempF,
        maxWindMph,
        maxWindKph,
        totalPrecipMm,
        totalPrecipIn,
        totalSnowCm,
        avgVisKm,
        avgVisMiles,
        avgHumidity,
        dailyWillItRain,
        dailyChanceOfRain,
        dailyWillItSnow,
        dailyChanceOfSnow,
        condition,
        uv
    )
}
