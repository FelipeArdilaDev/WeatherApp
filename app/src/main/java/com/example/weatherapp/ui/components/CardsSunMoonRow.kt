package com.example.weatherapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Nightlight
import androidx.compose.material.icons.outlined.WbTwilight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.domain.models.entity.RootForecastResponseEntity
import com.example.weatherapp.ui.theme.WeatherAppTheme

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
