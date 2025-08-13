package com.moscow.domain.repository.language

interface LanguageProvider {
    suspend fun setLanguage(language: String)
    fun getCurrentLanguage(): String
}
