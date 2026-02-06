package com.example.weatherapp.domain.result

sealed class AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>()
    data class Error(val error: AppError) : AppResult<Nothing>()
}

sealed class AppError {
    data object Network : AppError()
    data class Http(val code: Int, val message: String? = null) : AppError()
    data class Unknown(val message: String? = null) : AppError()
}