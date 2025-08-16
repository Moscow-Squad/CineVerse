package com.moscow.cineverse.screen.profile

import com.moscow.domain.model.language.AppLanguage


data class ProfileUIState(
     val name:String? = null,
     val username:String? = null,
     val image:String? = null,
     val sessionId:String = "",
     val accountId:String = "",
     val isLoading: Boolean = false,
     val errorMessage:String? = null,
     val isGuest:Boolean = false,
     val showLogoutBottomSheet: Boolean = false,
     val showEditProfileBottomSheet: Boolean = false,
     val showLanguageBottomSheet:Boolean = false,
     val showPreferencesBottomSheet:Boolean = false,
     val goToWebView: Boolean = false,
     val editProfileURL :String = "",
     val isDarkTheme:Boolean = true,
     val selectedPreference: String = "high",
     val currentLanguage: String = AppLanguage.English.code
)
