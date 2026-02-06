package com.example.weatherapp.data.network.models.response.dto

import com.example.weatherapp.domain.models.entity.ConditionResponseEntity

data class ConditionResponseDto(
    val text: String,
    val icon: String,
    val code: Int
)

fun ConditionResponseDto.mapToDomain() = ConditionResponseEntity(
    text = text,
    icon = icon,
    code = code
)
