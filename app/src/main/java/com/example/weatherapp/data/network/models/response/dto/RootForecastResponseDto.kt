package com.example.weatherapp.data.network.models.response.dto

import com.example.weatherapp.domain.models.entity.CurrentResponseEntity
import com.example.weatherapp.domain.models.entity.ForecastDayResponseEntity
import com.example.weatherapp.domain.models.entity.ForecastResponseEntity
import com.example.weatherapp.domain.models.entity.LocationResponseEntity
import com.example.weatherapp.domain.models.entity.RootForecastResponseEntity
import com.google.gson.annotations.SerializedName

data class RootForecastResponseDto(
    val location: LocationResponseDto,
    val current: CurrentResponseDto,
    val forecast: ForecastResponseDto,
)

data class LocationResponseDto(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    @SerializedName("tz_id")
    val tzId: String,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Double,
    val localtime: String
)

data class CurrentResponseDto(
    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Double,
    @SerializedName("last_updated")
    val lastUpdated: String,
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
    @SerializedName("vis_km")
    val visKm: Double,
    @SerializedName("vis_miles")
    val visMiles: Double,
    val uv: Double,
    @SerializedName("gust_mph")
    val gustMph: Double,
    @SerializedName("gust_kph")
    val gustKph: Double
)

data class ForecastResponseDto(
    @SerializedName("forecastday")
    val forecastDay: List<ForecastDayResponseDto>
)

data class ForecastDayResponseDto(
    val date: String,
    @SerializedName("date_epoch")
    val dateEpoch: Double,
    val day: DayResponseDto,
    val astro: AstroResponseDto,
    val hour: List<HourResponseDto>
)



fun RootForecastResponseDto.mapToDomain() = RootForecastResponseEntity(
    location = location.mapToDomain(),
    current = current.mapToDomain(),
    forecast = forecast.mapToDomain()
)

fun CurrentResponseDto.mapToDomain() = CurrentResponseEntity(
    lastUpdatedEpoch = lastUpdatedEpoch,
    lastUpdated = lastUpdated,
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
    visKm = visKm,
    visMiles = visMiles,
    uv = uv,
    gustMph = gustMph,
    gustKph = gustKph

)
fun LocationResponseDto.mapToDomain() = LocationResponseEntity(
    name = name,
    region = region,
    country = country,
    lat = lat,
    lon = lon,
    tzId = tzId,
    localtimeEpoch = localtimeEpoch,
    localtime = localtime
)

fun ForecastResponseDto.mapToDomain() = ForecastResponseEntity(
    forecastDay = forecastDay.map { it.mapToDomain() }
)

fun ForecastDayResponseDto.mapToDomain() = ForecastDayResponseEntity(
    date = date,
    dateEpoch = dateEpoch,
    day = day.mapToDomain(),
    astro = astro.mapToDomain(),
    hour = hour.map { it.mapToDomain() }

)
