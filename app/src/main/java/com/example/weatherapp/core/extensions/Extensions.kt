package com.example.weatherapp.core.extensions

import com.example.weatherapp.domain.result.AppError

fun AppError.toUiMessage(): String = when (this) {
    AppError.Network -> "Sin conexión. Revisa tu internet."
    is AppError.Http -> "Error del servidor (${code}). ${message.orEmpty()}".trim()
    is AppError.Unknown -> message ?: "Ocurrió un error inesperado."
}
