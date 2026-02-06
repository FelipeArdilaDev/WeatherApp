package com.example.weatherapp.ui.presentacion.ext

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

fun String.fixIconUrl(): String = if (startsWith("http")) this else "https:$this"
fun String.toDayShort(locale: Locale = Locale.getDefault()): String = try {
    val date = LocalDate.parse(this, DateTimeFormatter.ISO_LOCAL_DATE)
    date.dayOfWeek.getDisplayName(TextStyle.SHORT, locale)
        .replaceFirstChar { it.uppercase(locale) }
} catch (_: Exception) {
    this
}

fun String.toHourLabel(): String {
    return try {
        if (length >= 16) substring(11, 16) else this
    } catch (_: Exception) {
        this
    }
}

fun Double.toUvCategory(): String = when {
    this < 3 -> "Bajo"
    this < 6 -> "Moderado"
    this < 8 -> "Alto"
    this < 11 -> "Muy alto"
    else -> "Extremo"
}