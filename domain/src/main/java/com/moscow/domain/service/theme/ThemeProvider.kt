package com.moscow.domain.service.theme

import kotlinx.coroutines.flow.Flow

interface ThemeProvider {
    suspend fun changeAppTheme(isDark: Boolean)
    suspend fun clearTheme()
    val themeFlow: Flow<Boolean>
}