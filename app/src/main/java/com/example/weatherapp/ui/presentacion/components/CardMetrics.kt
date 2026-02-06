package com.example.weatherapp.ui.presentacion.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.domain.models.entity.RootForecastResponseEntity
import com.example.weatherapp.ui.presentacion.ext.toUvCategory
import com.example.weatherapp.ui.presentacion.model.MetricItem

@Composable
fun CardMetrics(model: RootForecastResponseEntity) {
    val current = model.current
    val items = listOf(
        MetricItem(
            title = stringResource(R.string.metric_uv_label),
            value = stringResource(
                R.string.metric_uv_value,
                current.uv.toString(),
                current.uv.toUvCategory()
            ),
            icon = Icons.Filled.WbSunny
        ),
        MetricItem(
            title = stringResource(R.string.metric_humidity_label),
            value = stringResource(R.string.metric_humidity_value, current.humidity.toInt()),
            icon = Icons.Filled.WaterDrop
        ),
        MetricItem(
            title = stringResource(R.string.metric_visibility_label),
            value = stringResource(R.string.metric_visibility_value, current.visKm.toInt()),
            icon = Icons.Filled.Visibility
        ),
        MetricItem(
            title = stringResource(R.string.metric_gusts_label),
            value = stringResource(R.string.metric_gusts_value, current.gustKph.toInt()),
            icon = Icons.Filled.Air
        )
    )

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetricTile(items[0], Modifier.weight(1f))
            MetricTile(items[1], Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetricTile(items[2], Modifier.weight(1f))
            MetricTile(items[3], Modifier.weight(1f))
        }
    }
}