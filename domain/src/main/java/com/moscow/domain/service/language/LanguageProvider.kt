package com.moscow.domain.service.language

interface LanguageProvider {
    suspend fun setLanguage(language: String)
    fun getCurrentLanguage(): String
}
