package com.moscow.cineverse.screen.profile

object ProfileScreenEffectHandler {
    fun handleEffect(
        effects: ProfileScreenEffects,
        navigateToLogin:()-> Unit,
        navigateToEditProfileBottomSheet:(String, String) -> Unit,
        navigateToMyRatings: () -> Unit,
        navigateToMyCollections: () -> Unit,
        navigateToMyHistory: () -> Unit,
    ){
        when(effects){
            ProfileScreenEffects.showEditProfileBottomSheet -> {
               // navigateToEditProfileBottomSheet()
            }
            ProfileScreenEffects.showLogoutBottomSheet -> {
                navigateToLogin()
            }

            ProfileScreenEffects.navigateToHistory -> {
                navigateToMyHistory()
            }
            ProfileScreenEffects.navigateToMyCollections -> {
                navigateToMyCollections()
            }
            ProfileScreenEffects.navigateToMyRating -> {
                navigateToMyRatings()
            }

            ProfileScreenEffects.onLogoutSuccessfully -> {

            }

            ProfileScreenEffects.onLogoutFailed -> {

            }

            ProfileScreenEffects.showLanguageBottomSheet -> TODO()
        }
    }
}