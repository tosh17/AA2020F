package ru.thstdio.aa2020.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response

private const val API_KEY_HEADER = "Authorization"
private const val BEARER_PREFIX = "Bearer "

class ApiHeaderInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .addHeader(API_KEY_HEADER, BEARER_PREFIX + apiKey)
            .build()
        return chain.proceed(request)
    }
}