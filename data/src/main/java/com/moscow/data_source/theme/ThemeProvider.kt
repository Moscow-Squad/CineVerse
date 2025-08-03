package com.moscow.data_source.theme

interface ThemeProvider {
    suspend fun initializeTheme()
    suspend fun changeAppTheme(isDark: Boolean)
    suspend fun getCurrentTheme(): Boolean
    suspend fun clearTheme()
    fun getCachedTheme(): Boolean
}