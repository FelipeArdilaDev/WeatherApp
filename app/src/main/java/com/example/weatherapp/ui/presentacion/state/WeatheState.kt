package com.example.weatherapp.ui.presentacion.state

import com.example.weatherapp.domain.models.entity.RootForecastResponseEntity
import com.example.weatherapp.domain.models.entity.SearchResponseEntity

data class WeatherState(
    val query: String = "",
    val searchResults: List<SearchResponseEntity> = emptyList(),
    val forecast: RootForecastResponseEntity? = null,
    val displayLocation: String? = null,
    val isSearching: Boolean = false,
    val isForecastLoading: Boolean = false,
    val errorMessage: String? = null,
    val showDropdown: Boolean = false
)

sealed interface WeatherEvent {
    data object ScreenShown : WeatherEvent
    data class QueryChanged(val query: String) : WeatherEvent
    data class LocationSelected(val name: String) : WeatherEvent

    data object LoadFromDeviceLocation : WeatherEvent
    data object LocationPermissionDenied : WeatherEvent
    data object Retry : WeatherEvent
}

sealed interface WeatherEffect {
    data object RequestLocationPermissions : WeatherEffect
    data class ShowSnackbar(val message: String) : WeatherEffect
}
