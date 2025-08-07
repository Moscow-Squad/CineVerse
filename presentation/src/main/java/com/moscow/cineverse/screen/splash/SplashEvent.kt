package com.moscow.cineverse.screen.splash

sealed class SplashEvent {
    data object NavigateToHome : SplashEvent()
    data object NavigateToLogin: SplashEvent()
    data object NavigateToOnboarding : SplashEvent()
}
