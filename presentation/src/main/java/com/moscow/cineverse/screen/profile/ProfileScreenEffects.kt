package com.moscow.cineverse.screen.profile

sealed class ProfileScreenEffects {
    data object navigateToEditProfile: ProfileScreenEffects()
    data object navigateToLogout: ProfileScreenEffects()
}