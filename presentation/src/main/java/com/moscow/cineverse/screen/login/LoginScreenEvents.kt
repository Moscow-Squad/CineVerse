package com.moscow.cineverse.screen.login

import com.moscow.cineverse.utlis.StringValue

sealed class LoginScreenEvents {
    data class ShowError(val message: StringValue): LoginScreenEvents()
    data object NavigateTo: LoginScreenEvents()
}