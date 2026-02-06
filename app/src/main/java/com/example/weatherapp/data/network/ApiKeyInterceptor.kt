package com.example.weatherapp.data.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(
    private val apiKey: String,
    private val paramName: String = KEY_PARAM
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url

        if (url.queryParameter(paramName) != null) {
            return chain.proceed(request)
        }

        val newUrl = url.newBuilder()
            .addQueryParameter(paramName, apiKey)
            .build()

        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }

    private companion object {
        const val KEY_PARAM = "key"
    }
}
