package com.example.weatherapp.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.weatherapp.domain.models.entity.RootForecastResponseEntity
import com.example.weatherapp.ui.presentacion.ext.fixIconUrl
import com.example.weatherapp.ui.presentacion.ext.toHourLabel
import kotlin.collections.forEach
import kotlin.collections.map
import kotlin.collections.orEmpty
import kotlin.collections.take

@Composable
fun CardHourlyForecast(model: RootForecastResponseEntity) {
    val hours = model.forecast.forecastDay.firstOrNull()?.hour.orEmpty()
    if (hours.isEmpty()) return

    val next12 = hours.take(12)
    val scroll = rememberScrollState()

    Column {
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            val itemWidth = 84.dp
            val chartHeight = 128.dp

            // ancho total (para que el canvas y el row se alineen)
            val totalWidth = itemWidth * next12.size

            Box(
                modifier = Modifier
                    .horizontalScroll(scroll)
                    .padding(horizontal = 10.dp, vertical = 12.dp)
            ) {
                // Canvas + columnas alineadas
                Box(
                    modifier = Modifier
                        .width(totalWidth)
                        .height(chartHeight)
                ) {
                    HourlyLineChartCanvas(
                        temps = next12.map { it.tempC.toFloat() },
                        itemWidth = itemWidth,
                        modifier = Modifier
                            .matchParentSize()
                            .clip(RoundedCornerShape(16.dp))
                    )

                    Row(
                        modifier = Modifier
                            .matchParentSize(),
                        horizontalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        next12.forEach { h ->
                            HourColumn(
                                time = h.time.toHourLabel(),
                                tempC = h.tempC.toInt(),
                                iconUrl = h.condition.icon.fixIconUrl(),
                                windKph = h.windKph.toInt(),
                                modifier = Modifier.width(itemWidth)
                            )
                        }
                    }
                }
            }
        }
    }
}