package com.moscow.domain.repository.language

interface LanguageProvider {
    suspend fun initializeLanguage(deviceLanguage: String)
    suspend fun setLanguage(language: String)
    suspend fun clearLanguageSettings()
    fun getCachedLanguage(): String
}
