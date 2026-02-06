package com.example.weatherapp.ui.presentacion.search_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.weatherapp.ui.components.CardHourlyForecast
import com.example.weatherapp.ui.components.CardMetrics
import com.example.weatherapp.ui.components.CardWeatherInfo
import com.example.weatherapp.ui.components.CardsSunMoonRow
import com.example.weatherapp.ui.components.EmptyState
import com.example.weatherapp.ui.components.ForecastDaysCards
import com.example.weatherapp.ui.components.InlineErrorCard
import com.example.weatherapp.ui.components.SearchDropdown
import com.example.weatherapp.ui.components.SearchPill
import com.example.weatherapp.ui.presentacion.state.WeatherState

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    state: WeatherState,
    snackbarHostState: SnackbarHostState,
    onQueryChange: (String) -> Unit,
    onSelectLocation: (String) -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(14.dp)
        ) {

            Column(Modifier.fillMaxSize()) {

                Box {
                    Column {
                        SearchPill(
                            query = state.query,
                            onQueryChange = onQueryChange
                        )

                        if (state.isSearching) {
                            Spacer(Modifier.height(6.dp))
                            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                        }
                    }

                    if (state.showDropdown) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 56.dp + 10.dp)
                                .zIndex(2f)
                        ) {
                            SearchDropdown(
                                query = state.query,
                                results = state.searchResults,
                                onSelect = { onSelectLocation(it.name) }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    val forecast = state.forecast
                    if (forecast != null) {
                        item {
                            val title = state.displayLocation ?: forecast.location.name
                            CardWeatherInfo(forecast, title = title)
                        }
                        item { ForecastDaysCards(forecast) }
                        item { CardHourlyForecast(forecast) }
                        item { CardMetrics(forecast) }
                        item { CardsSunMoonRow(forecast) }
                    } else {
                        item { EmptyState() }
                    }

                    state.errorMessage?.let { msg ->
                        item { InlineErrorCard(message = msg, onRetry = onRetry) }
                    }
                }
            }
            if (state.isForecastLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.35f))
                        .zIndex(3f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(strokeWidth = 4.dp)
                }
            }
        }
    }
}
