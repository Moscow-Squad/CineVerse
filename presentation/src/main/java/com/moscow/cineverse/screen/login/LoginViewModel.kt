package com.moscow.cineverse.screen.login

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cinverse.presentation.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(
    private val context: Context
) : BaseViewModel<LoginScreenState, LoginScreenEvents>(LoginScreenState()),
    LoginInteractionListener {

    private var usernameValidationJob: Job? = null
    private var passwordValidationJob: Job? = null

    override fun onUsernameValueChanged(username: String) {
        updateState { it.copy(username = username) }
        usernameValidationJob?.cancel()

        usernameValidationJob = viewModelScope.launch {
            delay(500)
            val trimmedUsername = username.trim()
            val isValid = trimmedUsername.length in 4..32 && trimmedUsername.all { it.isLetterOrDigit() }
            updateState {
                it.copy(
                    usernameError = if (!isValid && uiState.value.username.isNotEmpty())
                        context.getString(R.string.usernames_can_only_4_32_letters_and_numbers) else null
                )
            }
        }
    }

    override fun onPasswordValueChanged(password: String) {
        updateState { it.copy(password = password) }
        passwordValidationJob?.cancel()

        passwordValidationJob = viewModelScope.launch {
            delay(500)
            val isValid = password.length in 4..100
            updateState {
                it.copy(
                    passwordError = if (!isValid && uiState.value.password.isNotEmpty())
                        context.getString(R.string.password_can_only_4_100_characters) else null
                )
            }
        }
    }

    override fun onClickLogin() {
        updateState { it.copy(isLoading = uiState.value.username.isNotBlank() &&
                uiState.value.password.isNotBlank()) }
    }

    override fun onClickJoinAsGuest() {
        TODO("Not yet implemented")
    }

    override fun onClickCreateNewAccount() {
        TODO("Not yet implemented")
    }

}