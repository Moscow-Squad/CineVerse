package com.moscow.cineverse.screen.login

sealed class LoginScreenEvents {
    data class ShowError(val message: String): LoginScreenEvents()
    data object NavigateTo: LoginScreenEvents()
}