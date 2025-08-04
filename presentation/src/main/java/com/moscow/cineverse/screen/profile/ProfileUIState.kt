package com.moscow.cineverse.screen.profile

data class ProfileUIState(
    private val name:String? = null,
    private val username:String? = null,
    private val image:String? = null,
    private val isLoading: Boolean = false,
    private val errorMessage:String? = null,
    val isDarkTheme:Boolean = true
)
