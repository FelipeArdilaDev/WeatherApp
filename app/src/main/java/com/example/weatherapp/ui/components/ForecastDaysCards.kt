package com.example.weatherapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.weatherapp.R
import com.example.weatherapp.domain.models.entity.RootForecastResponseEntity
import com.example.weatherapp.ui.presentacion.ext.fixIconUrl
import com.example.weatherapp.ui.presentacion.ext.toDayShort

@Composable
fun ForecastDaysCards(model: RootForecastResponseEntity) {
    val days = model.forecast.forecastDay.take(3)
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(days, key = { it.dateEpoch }) { foreCast ->
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
            ) {
                Column(
                    modifier = Modifier
                        .width(112.dp)
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        foreCast.date.toDayShort(),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(Modifier.height(6.dp))
                    AsyncImage(
                        model = foreCast.day.condition.icon.fixIconUrl(),
                        contentDescription = foreCast.day.condition.text,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        stringResource(R.string.lbl_celsius, foreCast.day.avgTempC.toInt()),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        foreCast.day.condition.text,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}