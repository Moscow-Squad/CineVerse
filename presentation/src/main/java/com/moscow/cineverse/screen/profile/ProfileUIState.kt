package com.moscow.cineverse.screen.profile

import java.util.Locale

data class ProfileUIState(
     val name:String? = null,
     val username:String? = null,
     val image:String? = null,
     val sessionId:String = "",
     val isLoading: Boolean = false,
     val errorMessage:String? = null,
     val isGuest:Boolean = false,
     val showLogoutBottomSheet: Boolean = false,
     val showEditProfileBottomSheet: Boolean = false,
     val showLanguageBottomSheet:Boolean = false
     val isDarkTheme:Boolean = true,
     val appLanguage:String = Locale.getDefault().language
)
