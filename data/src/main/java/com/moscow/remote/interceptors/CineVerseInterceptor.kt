package com.moscow.remote.interceptors

import com.moscow.data.BuildConfig
import com.moscow.data_source.system.LanguageProvider
import okhttp3.Interceptor
import okhttp3.Response

class CineVerseInterceptor(
    private val languageProvider: LanguageProvider
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newRequest = originalRequest.newBuilder()
            .url(originalUrl)
            .header("Accept-Language", languageProvider.getCurrentLanguage())
            .header("Authorization", "Bearer ${BuildConfig.BEARER_TOKEN}")
            .build()

        return chain.proceed(newRequest)
    }
}
