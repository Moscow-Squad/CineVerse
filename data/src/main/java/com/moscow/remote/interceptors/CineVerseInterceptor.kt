package com.moscow.remote.interceptors

import com.moscow.data.BuildConfig
import com.moscow.domain.repository.language.LanguageProvider
import okhttp3.Interceptor
import okhttp3.Response

class CineVerseInterceptor(
    private val languageProvider: LanguageProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val language = languageProvider.getCurrentLanguage()

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("language", language)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .header("Authorization", "Bearer ${BuildConfig.BEARER_TOKEN}")
            .build()

        return chain.proceed(newRequest)
    }
}