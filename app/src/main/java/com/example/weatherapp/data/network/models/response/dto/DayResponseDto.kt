package com.example.weatherapp.data.network.models.response.dto

import com.example.weatherapp.domain.models.entity.DayResponseEntity
import com.google.gson.annotations.SerializedName

data class DayResponseDto(
    @SerializedName("maxtemp_c")
    val maxTempC: Double,
    @SerializedName("maxtemp_f")
    val maxTempF: Double,
    @SerializedName("mintemp_c")
    val minTempC: Double,
    @SerializedName("mintemp_f")
    val minTempF: Double,
    @SerializedName("avgtemp_c")
    val avgTempC: Double,
    @SerializedName("avgtemp_f")
    val avgTempF: Double,
    @SerializedName("maxwind_mph")
    val maxWindMph: Double,
    @SerializedName("maxwind_kph")
    val maxWindKph: Double,
    @SerializedName("totalprecip_mm")
    val totalPrecipMm: Double,
    @SerializedName("totalprecip_in")
    val totalPrecipIn: Double,
    @SerializedName("totalsnow_cm")
    val totalSnowCm: Double,
    @SerializedName("avgvis_km")
    val avgVisKm: Double,
    @SerializedName("avgvis_miles")
    val avgVisMiles: Double,
    @SerializedName("avghumidity")
    val avgHumidity: Double,
    @SerializedName("daily_will_it_rain")
    val dailyWillItRain: Double,
    @SerializedName("daily_chance_of_rain")
    val dailyChanceOfRain: Double,
    @SerializedName("daily_will_it_snow")
    val dailyWillItSnow: Double,
    @SerializedName("daily_chance_of_snow")
    val dailyChanceOfSnow: Double,
    val condition: ConditionResponseDto,
    val uv: Double
)

fun DayResponseDto.mapToDomain() = DayResponseEntity(
    maxTempC = maxTempC,
    maxTempF = maxTempF,
    minTempC = minTempC,
    minTempF = minTempF,
    avgTempC = avgTempC,
    avgTempF = avgTempF,
    maxWindMph = maxWindMph,
    maxWindKph = maxWindKph,
    totalPrecipMm = totalPrecipMm,
    totalPrecipIn = totalPrecipIn,
    totalSnowCm = totalSnowCm,
    avgVisKm = avgVisKm,
    avgVisMiles = avgVisMiles,
    avgHumidity = avgHumidity,
    dailyWillItRain = dailyWillItRain,
    dailyChanceOfRain = dailyChanceOfRain,
    dailyWillItSnow = dailyWillItSnow,
    dailyChanceOfSnow = dailyChanceOfSnow,
    condition = condition.mapToDomain(),
    uv = uv

)
