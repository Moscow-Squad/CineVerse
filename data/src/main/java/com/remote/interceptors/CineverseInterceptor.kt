package com.remote.interceptors

import com.moscow.cineverse.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

class CineverseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .header("Accept-Language", Locale.getDefault().language)
            .build()

        return chain.proceed(newRequest)
    }
}