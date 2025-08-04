package com.moscow.domain.repository.language

import kotlinx.coroutines.flow.Flow

interface LanguageProvider {
    suspend fun initializeLanguage(deviceLanguage: String)
    suspend fun setLanguage(language: String)
    suspend fun clearLanguageSettings()

    val languageFlow: Flow<String>
}
