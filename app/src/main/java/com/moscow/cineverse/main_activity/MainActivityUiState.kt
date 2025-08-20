package com.moscow.cineverse.main_activity

import com.moscow.cineverse.navigation.AppDestination
import com.moscow.cineverse.navigation.routes.OnBoardingRoute

data class MainActivityUiState(
    val isLoading: Boolean = true,
    val isDarkTheme: Boolean = false,
    val language: String = "en",
    val startDestination: AppDestination = OnBoardingRoute
)