package com.example.weatherapp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

@Composable
fun HourlyLineChartCanvas(
    temps: List<Float>,
    itemWidth: Dp,
    modifier: Modifier = Modifier
) {
    val outline = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
    val lineColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.65f)
    val dotColor = MaterialTheme.colorScheme.primary

    Canvas(modifier = modifier) {
        if (temps.isEmpty()) return@Canvas

        val height = size.height

        // Márgenes verticales para dejar espacio a labels/íconos
        val topPad = 14.dp.toPx()
        val bottomPad = 44.dp.toPx()

        val minT = temps.minOrNull() ?: 0f
        val maxT = temps.maxOrNull() ?: 0f
        val range = max(1f, maxT - minT)

        // X por columna (centro de cada item)
        val itemPx = itemWidth.toPx()
        fun xAt(i: Int) = (itemPx * i) + itemPx / 2f

        // Y mapeado (más caliente = más arriba)
        fun yAt(t: Float): Float {
            val norm = (t - minT) / range
            return (height - bottomPad) - norm * (height - topPad - bottomPad)
        }

        // Guías verticales punteadas
        val dash = PathEffect.dashPathEffect(floatArrayOf(8f, 8f), 0f)
        for (i in temps.indices) {
            val x = xAt(i)
            drawLine(
                color = outline,
                start = Offset(x, topPad),
                end = Offset(x, height - bottomPad),
                strokeWidth = 2f,
                pathEffect = dash
            )
        }

        // Línea (path) + puntos
        val path = Path()
        temps.forEachIndexed { i, t ->
            val x = xAt(i)
            val y = yAt(t)
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }

        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 4f, cap = StrokeCap.Round)
        )

        temps.forEachIndexed { i, t ->
            val x = xAt(i)
            val y = yAt(t)

            // círculo blanco de fondo (como en el mock)
            drawCircle(
                color = Color.White,
                radius = 9f,
                center = Offset(x, y)
            )
            // borde
            drawCircle(
                color = outline,
                radius = 9f,
                center = Offset(x, y),
                style = Stroke(width = 2f)
            )
            // punto
            drawCircle(
                color = dotColor,
                radius = 4.5f,
                center = Offset(x, y)
            )
        }
    }
}