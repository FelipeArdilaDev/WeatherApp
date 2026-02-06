package com.example.weatherapp.ui.presentacion.search_screen

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.ui.presentacion.state.WeatherEffect
import com.example.weatherapp.ui.presentacion.state.WeatherEvent
import com.example.weatherapp.ui.presentacion.WeatherViewModel
import kotlinx.coroutines.launch

@Composable
fun WeatherRoute(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    //Launcher permisos
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val fineGranted = result[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarseGranted = result[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (fineGranted || coarseGranted) {
            viewModel.onEvent(WeatherEvent.LoadFromDeviceLocation)
        } else {
            viewModel.onEvent(WeatherEvent.LocationPermissionDenied)
        }
    }

    // chequear permisos
    fun hasLocationPermission(): Boolean {
        val fine = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarse = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return fine || coarse
    }

    // 2) effects (one-shot)
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                WeatherEffect.RequestLocationPermissions -> {
                    if (hasLocationPermission()) {
                        viewModel.onEvent(WeatherEvent.LoadFromDeviceLocation)
                    } else {
                        permissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }
                }
                is WeatherEffect.ShowSnackbar -> {
                    scope.launch { snackbarHostState.showSnackbar(effect.message) }
                }
            }
        }
    }

    // 3) Primera entrada a la pantalla
    LaunchedEffect(Unit) {
        viewModel.onEvent(WeatherEvent.ScreenShown)
    }

    // 4) UI
    WeatherScreen(
        modifier = modifier,
        state = state,
        snackbarHostState = snackbarHostState,
        onQueryChange = { viewModel.onEvent(WeatherEvent.QueryChanged(it)) },
        onSelectLocation = { viewModel.onEvent(WeatherEvent.LocationSelected(it)) },
        onRetry = { viewModel.onEvent(WeatherEvent.Retry) }
    )
}
