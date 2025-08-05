package com.moscow.cineverse.screen.profile

sealed class ProfileScreenEffects {
    data object showLanguageBottomSheet: ProfileScreenEffects()
    data object showEditProfileBottomSheet: ProfileScreenEffects()
    data object showLogoutBottomSheet: ProfileScreenEffects()
    data object navigateToMyRating: ProfileScreenEffects()
    data object navigateToMyCollections: ProfileScreenEffects()
    data object navigateToHistory: ProfileScreenEffects()
    data object onLogoutSuccessfully: ProfileScreenEffects()
    data object onLogoutFailed: ProfileScreenEffects()
}