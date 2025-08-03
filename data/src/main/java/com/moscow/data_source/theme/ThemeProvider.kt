package com.moscow.data_source.theme

interface ThemeProvider {
    suspend fun initializeTheme()
    suspend fun changeAppTheme(theme: String)
    suspend fun getAppTheme(): String
}