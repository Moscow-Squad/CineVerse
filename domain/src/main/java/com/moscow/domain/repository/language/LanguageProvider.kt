package com.moscow.domain.repository.language

import kotlinx.coroutines.flow.Flow

interface LanguageProvider {
    suspend fun initializeLanguage(deviceLanguage: String)
    suspend fun setLanguage(language: String)
    suspend fun clearLanguageSettings()
    fun getCachedLanguage(): String
    val languageFlow: Flow<String>
}
