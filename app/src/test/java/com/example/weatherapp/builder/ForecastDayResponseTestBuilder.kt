package com.example.weatherapp.builder

import com.example.weatherapp.domain.models.entity.AstroResponseEntity
import com.example.weatherapp.domain.models.entity.DayResponseEntity
import com.example.weatherapp.domain.models.entity.HourResponseEntity

data class ForecastDayResponseEntity(
    val date: String = "",
    val dateEpoch: Double = 0.0,
    val day: DayResponseEntity = DayResponseTestBuilder().build(),
    val astro: AstroResponseEntity = AstroResponseTestBuilder().build(),
    val hour: List<HourResponseEntity> = listOf(),
)
