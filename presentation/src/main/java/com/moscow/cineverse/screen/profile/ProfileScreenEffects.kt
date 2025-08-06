package com.moscow.cineverse.screen.profile

sealed class ProfileScreenEffects {
    data class  GoToWebView(val url:String): ProfileScreenEffects()
    data object OnLoginClick: ProfileScreenEffects()
    data object NavigateToMyRating: ProfileScreenEffects()
    data object NavigateToMyCollections: ProfileScreenEffects()
    data object NavigateToHistory: ProfileScreenEffects()
    data object OnLogoutSuccessfully: ProfileScreenEffects()
    data object OnLogoutFailed: ProfileScreenEffects()
}