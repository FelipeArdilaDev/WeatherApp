package com.example.weatherapp.ui.presentacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.extensions.toUiMessage
import com.example.weatherapp.data.di.qualifers.IoDispatcher
import com.example.weatherapp.data.location.LocationProvider
import com.example.weatherapp.domain.models.entity.SearchResponseEntity
import com.example.weatherapp.domain.result.AppError
import com.example.weatherapp.domain.result.AppResult
import com.example.weatherapp.domain.usecase.GetForecastUseCase
import com.example.weatherapp.domain.usecase.SearchLocationUseCase
import com.example.weatherapp.ui.presentacion.model.DeviceLocation
import com.example.weatherapp.ui.presentacion.state.WeatherEffect
import com.example.weatherapp.ui.presentacion.state.WeatherEvent
import com.example.weatherapp.ui.presentacion.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val searchLocationUseCase: SearchLocationUseCase,
    private val getForecastUseCase: GetForecastUseCase,
    private val locationProvider: LocationProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<WeatherEffect>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val effect: SharedFlow<WeatherEffect> = _effect.asSharedFlow()

    private val events = MutableSharedFlow<WeatherEvent>(
        replay = 0,
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun onEvent(event: WeatherEvent) {
        events.tryEmit(event)
    }

    init {
        bind()
    }

    private fun bind() {
        events.filterIsInstance<WeatherEvent.ScreenShown>()
            .onEach { _effect.emit(WeatherEffect.RequestLocationPermissions) }
            .launchIn(viewModelScope)

        events.filterIsInstance<WeatherEvent.Retry>()
            .onEach { _effect.emit(WeatherEffect.RequestLocationPermissions) }
            .launchIn(viewModelScope)

        events.filterIsInstance<WeatherEvent.LocationPermissionDenied>()
            .onEach {
                _state.update {
                    it.copy(
                        isSearching = false,
                        isForecastLoading = false,
                        errorMessage = "Permiso de ubicación denegado. Puedes buscar manualmente."
                    )
                }
                _effect.emit(WeatherEffect.ShowSnackbar("Permiso de ubicación denegado"))
            }
            .launchIn(viewModelScope)

        events.filterIsInstance<WeatherEvent.LoadFromDeviceLocation>()
            .onEach { loadForecastFromDeviceLocation() }
            .launchIn(viewModelScope)

        events.filterIsInstance<WeatherEvent.LocationSelected>()
            .onEach { e ->
                _state.update {
                    it.copy(
                        query = "",
                        searchResults = emptyList(),
                        showDropdown = false,
                        isSearching = false,
                        isForecastLoading = true,
                        errorMessage = null,
                        displayLocation = null
                    )
                }
                loadForecast(query = e.name)
            }
            .launchIn(viewModelScope)

        events.filterIsInstance<WeatherEvent.QueryChanged>()
            .onEach { e ->
                val raw = e.query
                val trimmed = raw.trim()

                _state.update { s ->
                    s.copy(
                        query = raw,
                        errorMessage = null,
                        searchResults = if (trimmed.length < 3) emptyList() else s.searchResults,
                        showDropdown = trimmed.length >= 3 && s.searchResults.isNotEmpty(),
                        isSearching = false
                    )
                }
            }
            .map { it.query.trim() }
            .debounce(350)
            .distinctUntilChanged()
            .flatMapLatest { q ->
                flow {
                    if (q.length < 3) {
                        emit(SearchResult.Empty)
                        return@flow
                    }

                    emit(SearchResult.Loading)

                    emit(
                        when (val res = searchLocationUseCase(q)) {
                            is AppResult.Success -> SearchResult.Success(res.data)
                            is AppResult.Error -> SearchResult.Failure(res.error)
                        }
                    )
                }
            }
            .onEach { r ->
                when (r) {
                    SearchResult.Empty -> _state.update {
                        it.copy(
                            isSearching = false,
                            searchResults = emptyList(),
                            showDropdown = false
                        )
                    }

                    SearchResult.Loading -> _state.update {
                        it.copy(isSearching = true, errorMessage = null)
                    }

                    is SearchResult.Success -> _state.update { s ->
                        s.copy(
                            isSearching = false,
                            searchResults = r.items,
                            showDropdown = s.query.trim().length >= 3 && r.items.isNotEmpty(),
                            errorMessage = null
                        )
                    }

                    is SearchResult.Failure -> {
                        _state.update {
                            it.copy(
                                isSearching = false,
                                searchResults = emptyList(),
                                showDropdown = false,
                                errorMessage = r.error.toUiMessage()
                            )
                        }
                        _effect.emit(WeatherEffect.ShowSnackbar("Error buscando ubicación"))
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadForecastFromDeviceLocation() {
        viewModelScope.launch(ioDispatcher) {
            _state.update {
                it.copy(
                    isForecastLoading = true,
                    isSearching = false,
                    errorMessage = null,
                    searchResults = emptyList(),
                    showDropdown = false
                )
            }

            val loc: DeviceLocation = runCatching {
                locationProvider.getLastKnownLocation()
            }.getOrElse {
                _state.update {
                    it.copy(
                        isForecastLoading = false,
                        forecast = null,
                        errorMessage = "No se pudo obtener tu ubicación. Intenta de nuevo."
                    )
                }
                _effect.emit(WeatherEffect.ShowSnackbar("No se pudo obtener ubicación"))
                return@launch
            }
            val placeName = loc.locality ?: loc.adminArea
            _state.update { it.copy(displayLocation = placeName) }

            val query = "${loc.latitude},${loc.longitude}"
            loadForecast(query)
        }
    }

    private fun loadForecast(query: String) {
        viewModelScope.launch(ioDispatcher) {
            _state.update { it.copy(isForecastLoading = true, errorMessage = null) }

            when (val result = getForecastUseCase(query)) {
                is AppResult.Success -> {
                    _state.update {
                        it.copy(
                            isForecastLoading = false,
                            forecast = result.data,
                            showDropdown = false,
                            errorMessage = null
                        )
                    }
                }

                is AppResult.Error -> {
                    _state.update {
                        it.copy(
                            isForecastLoading = false,
                            forecast = null,
                            errorMessage = result.error.toUiMessage()
                        )
                    }
                    _effect.emit(WeatherEffect.ShowSnackbar("Error cargando pronóstico"))
                }
            }
        }
    }

    private sealed interface SearchResult {
        data object Empty : SearchResult
        data object Loading : SearchResult
        data class Success(val items: List<SearchResponseEntity>) : SearchResult
        data class Failure(val error: AppError) : SearchResult
    }
}
