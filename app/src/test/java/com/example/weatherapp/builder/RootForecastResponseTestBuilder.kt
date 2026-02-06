package com.example.weatherapp.builder

import com.example.weatherapp.data.network.models.response.dto.CurrentResponseDto
import com.example.weatherapp.data.network.models.response.dto.ForecastResponseDto
import com.example.weatherapp.data.network.models.response.dto.LocationResponseDto
import com.example.weatherapp.data.network.models.response.dto.RootForecastResponseDto
import com.example.weatherapp.data.network.models.response.dto.mapToDomain
import com.example.weatherapp.domain.models.entity.RootForecastResponseEntity

data class RootForecastResponseTestBuilder(
    val location: LocationResponseDto = LocationResponseTestBuilder().buildToDto(),
    val current: CurrentResponseDto = CurrentResponseTestBuilder().buildToDto(),
    val forecast: ForecastResponseDto = ForecastResponseTestBuilder().buildToDto(),
) {
    fun build() = RootForecastResponseEntity(
        location.mapToDomain(),
        current.mapToDomain(),
        forecast.mapToDomain()
    )

    fun buildToDto() = RootForecastResponseDto(
        location,
        current,
        forecast
    )

}