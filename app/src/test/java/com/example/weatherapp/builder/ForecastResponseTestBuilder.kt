package com.example.weatherapp.builder

import com.example.weatherapp.data.network.models.response.dto.ForecastResponseDto
import com.example.weatherapp.domain.models.entity.ForecastDayResponseEntity
import com.example.weatherapp.domain.models.entity.ForecastResponseEntity

data class ForecastResponseTestBuilder(
    val forecastDay: List<ForecastDayResponseEntity> = listOf()
){
    fun build() = ForecastResponseEntity(
        listOf()
    )

    fun buildToDto() = ForecastResponseDto(
        listOf()
    )
}