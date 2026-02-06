package com.example.weatherapp.data.network.models.response.dto

import com.example.weatherapp.domain.models.entity.HourResponseEntity
import com.google.gson.annotations.SerializedName

data class HourResponseDto(
    @SerializedName("time_epoch")
    val timeEpoch: Double,
    val time: String,
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("temp_f")
    val tempF: Double,
    @SerializedName("is_day")
    val isDay: Double,
    val condition: ConditionResponseDto,
    @SerializedName("wind_mph")
    val windMph: Double,
    @SerializedName("wind_kph")
    val windKph: Double,
    @SerializedName("wind_degree")
    val windDegree: Double,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("pressure_mb")
    val pressureMb: Double,
    @SerializedName("pressure_in")
    val pressureIn: Double,
    @SerializedName("precip_mm")
    val precipMm: Double,
    @SerializedName("precip_in")
    val precipIn: Double,
    @SerializedName("snow_cm")
    val snowCm: Double,
    val humidity: Double,
    val cloud: Double,
    @SerializedName("feelslike_c")
    val feelsLikeC: Double,
    @SerializedName("feelslike_f")
    val feelsLikeF: Double,
    @SerializedName("windchill_c")
    val windchillC: Double,
    @SerializedName("windchill_f")
    val windchillF: Double,
    @SerializedName("heatindex_c")
    val heatIndexC: Double,
    @SerializedName("heatindex_f")
    val heatIndexF: Double,
    @SerializedName("dewpoint_c")
    val dewPointC: Double,
    @SerializedName("dewpoint_f")
    val dewPointF: Double,
    @SerializedName("will_it_rain")
    val willItRain: Double,
    @SerializedName("chance_of_rain")
    val chanceOfRain: Double,
    @SerializedName("will_it_snow")
    val willItSnow: Double,
    @SerializedName("chance_of_snow")
    val chanceOfSnow: Double,
    @SerializedName("vis_km")
    val visKm: Double,
    @SerializedName("vis_miles")
    val visMiles: Double,
    @SerializedName("gust_mph")
    val gustMph: Double,
    @SerializedName("gust_kph")
    val gustKph: Double,
    val uv: Double
)

fun HourResponseDto.mapToDomain() = HourResponseEntity(
    timeEpoch = timeEpoch,
    time = time,
    tempC = tempC,
    tempF = tempF,
    isDay = isDay,
    condition = condition.mapToDomain(),
    windMph = windMph,
    windKph = windKph,
    windDegree = windDegree,
    windDir = windDir,
    pressureMb = pressureMb,
    pressureIn = pressureIn,
    precipMm = precipMm,
    precipIn = precipIn,
    snowCm = snowCm,
    humidity = humidity,
    cloud = cloud,
    feelsLikeC = feelsLikeC,
    feelsLikeF = feelsLikeF,
    windchillC = windchillC,
    windchillF = windchillF,
    heatIndexC = heatIndexC,
    heatIndexF = heatIndexF,
    dewPointC = dewPointC,
    dewPointF = dewPointF,
    willItRain = willItRain,
    chanceOfRain = chanceOfRain,
    willItSnow = willItSnow,
    chanceOfSnow = chanceOfSnow,
    visKm = visKm,
    visMiles = visMiles,
    gustMph = gustMph,
    gustKph = gustKph,
    uv = uv

)
