package com.moscow.cineverse.screen.profile

import java.util.Locale

data class ProfileUIState(
    private val name:String? = null,
    private val username:String? = null,
    private val image:String? = null,
    private val isLoading: Boolean = false,
    private val errorMessage:String? = null,
    val isDarkTheme:Boolean = true,
    val appLanguage:String = Locale.getDefault().language
)
