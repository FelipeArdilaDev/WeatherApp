package com.example.weatherapp.ui.presentacion.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.weatherapp.R
import com.example.weatherapp.domain.models.entity.RootForecastResponseEntity
import com.example.weatherapp.ui.presentacion.ext.fixIconUrl


@Composable
fun CardWeatherInfo(model: RootForecastResponseEntity,title: String) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Text(
                            stringResource(
                                R.string.txt_card_weather_info_region,
                                model.location.region,
                                model.location.country
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            stringResource(R.string.lbl_celsius, model.current.tempC.toInt()),
                            style = MaterialTheme.typography.displayLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            model.current.condition.text,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }

                    AsyncImage(
                        model = model.current.condition.icon.fixIconUrl(),
                        contentDescription = model.current.condition.text,
                        modifier = Modifier.size(84.dp)
                    )
                }

                Spacer(Modifier.height(10.dp))
            }
        }
    }
}