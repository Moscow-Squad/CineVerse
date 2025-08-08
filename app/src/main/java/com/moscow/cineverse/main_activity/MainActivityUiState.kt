package com.moscow.cineverse.main_activity

data class MainActivityUiState(
    val isLoading: Boolean = false,
    val isDarkTheme: Boolean = false,
    val language: String = "en"
)