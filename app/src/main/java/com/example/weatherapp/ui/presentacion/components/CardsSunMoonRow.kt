package com.example.weatherapp.ui.presentacion.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Nightlight
import androidx.compose.material.icons.outlined.WbTwilight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.domain.models.entity.RootForecastResponseEntity

@Composable
fun CardsSunMoonRow(model: RootForecastResponseEntity) {
    val today = model.forecast.forecastDay.firstOrNull() ?: return

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            SunMoonTile(
                stringResource(R.string.card_title_sunrise),
                today.astro.sunrise,
                Icons.Outlined.WbTwilight,
                Modifier.weight(1f),
            )
            SunMoonTile(
                stringResource(R.string.card_title_moonrise),
                today.astro.moonrise,
                Icons.Outlined.Nightlight,
                Modifier.weight(1f)
            )
        }
        MoonCard(
            title = today.astro.moonPhase,
            illuminationPercent = today.astro.moonIllumination.toInt(),
            moonrise = today.astro.moonrise
        )
    }
}
