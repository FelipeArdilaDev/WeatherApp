package com.example.weatherapp.data.network.utils

import com.example.weatherapp.domain.result.AppError
import com.example.weatherapp.domain.result.AppResult
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

private fun Throwable.toAppError(): AppError = when (this) {
    is UnknownHostException,
    is SocketTimeoutException,
    is IOException -> AppError.Network
    is HttpException -> AppError.Http(code(), message())
    else -> AppError.Unknown(message)
}

private inline fun <T> Response<T>.bodyOrThrow(): T {
    val b = body()
    if (isSuccessful && b != null) return b
    throw HttpException(this)
}

suspend fun <T, R> safeApiCall(
    call: suspend () -> Response<T>,
    mapper: (T) -> R
): AppResult<R> {
    return runCatching {
        mapper(call().bodyOrThrow())
    }.fold(
        onSuccess = { AppResult.Success(it) },
        onFailure = { AppResult.Error(it.toAppError()) }
    )
}