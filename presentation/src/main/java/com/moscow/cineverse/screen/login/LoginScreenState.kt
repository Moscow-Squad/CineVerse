package com.moscow.cineverse.screen.login

import com.moscow.cineverse.utlis.StringValue

data class LoginScreenState(
    val username: String = "",
    val password: String = "",
    val usernameError: StringValue? = null,
    val passwordError: StringValue? = null,
    val isLoading: Boolean = false,
    val showSignUpBottomSheet: Boolean = false,
    val urlWebView: String = "",
    val showWebView: Boolean = false
)
