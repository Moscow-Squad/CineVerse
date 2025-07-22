package com.remote.interceptors

import com.moscow.cineverse.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

class CineverseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newRequest = originalRequest.newBuilder()
            .url(originalUrl)
            .header("Accept-Language", Locale.getDefault().language)
            .header("Authorization", "Bearer ${BuildConfig.BEARER_TOKEN}")
            .build()

        return chain.proceed(newRequest)
    }
}