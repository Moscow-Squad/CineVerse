package com.moscow.domain.service.language

import kotlinx.coroutines.flow.StateFlow

interface LanguageProvider {
    val currentLanguage: StateFlow<String>
    suspend fun setLanguage(language: String)
}
