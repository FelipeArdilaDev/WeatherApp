package com.example.weatherapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val WeatherLightColorScheme = lightColorScheme(
    primary = SkyBlue,
    onPrimary = OceanBlue,

    secondary = CloudBlue,

    background = Mist,

    surface = SurfaceLight,
)

private val WeatherDarkColorScheme = darkColorScheme(
    primary = SkyNight,
    onPrimary = Midnight,

    secondary = CloudNight,
    onSecondary = Midnight,

    tertiary = MoonGold,
    onTertiary = Midnight,

    background = Midnight,
    onBackground = Mist,

    surface = DeepOcean,
    onSurface = Mist,

    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,

    outline = OutlineDark
)

@Composable
fun WeatherAppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) WeatherDarkColorScheme else WeatherLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
