package com.example.weatherapp.builder

import com.example.weatherapp.data.network.models.response.dto.ConditionResponseDto
import com.example.weatherapp.domain.models.entity.ConditionResponseEntity

data class ConditionResponseTestBuilder(
    val text: String = "Sunny",
    val icon: String = "//cdn.weatherapi.com/weather/64x64/day/113.png",
    val code: Int = 1000
) {
    fun build() = ConditionResponseEntity(text, icon, code)
    fun buildToDto() = ConditionResponseDto(text, icon, code)

}