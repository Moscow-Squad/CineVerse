package com.moscow.cineverse.screen.on_boarding

sealed class OnBoardingScreenEvents {
    data object NavigateToLoginScreen: OnBoardingScreenEvents()
}