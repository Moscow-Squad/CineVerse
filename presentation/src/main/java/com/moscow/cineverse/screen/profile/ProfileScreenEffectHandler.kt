package com.moscow.cineverse.screen.profile

object ProfileScreenEffectHandler {
    fun handleEffect(
        effects: ProfileScreenEffects,
        navigateToLogin:()->Unit,
        onLogoutFailed:()-> Unit,
        navigateToEditProfileBottomSheet:(String, String) -> Unit,
        navigateToMyRatings: () -> Unit,
        navigateToMyCollections: () -> Unit,
        navigateToMyHistory: () -> Unit,
    ){
        when(effects){

            ProfileScreenEffects.NavigateToHistory -> {
                navigateToMyHistory()
            }
            ProfileScreenEffects.NavigateToMyCollections -> {
                navigateToMyCollections()
            }
            ProfileScreenEffects.NavigateToMyRating -> {
                navigateToMyRatings()
            }

            ProfileScreenEffects.OnLogoutSuccessfully -> {
                navigateToLogin()
            }

            ProfileScreenEffects.OnLogoutFailed -> {
                onLogoutFailed()
            }

            is ProfileScreenEffects.GoToWebView -> {

            }

            ProfileScreenEffects.OnLoginClick -> {
                navigateToLogin()
            }
        }
    }
}