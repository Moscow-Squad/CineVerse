package com.moscow.cineverse.screen.profile

object ProfileScreenEffectHandler {
    fun handleEffect(
        effects: ProfileScreenEffects,
        navigateToLogoutBottomSheet:(String)-> Unit,
        navigateToEditProfileBottomSheet:(String, String) -> Unit
    ){
        when(effects){
            ProfileScreenEffects.navigateToEditProfile -> {
               // navigateToEditProfileBottomSheet()
            }
            ProfileScreenEffects.navigateToLogout -> {
               // navigateToLogoutBottomSheet()
            }
        }
    }
}