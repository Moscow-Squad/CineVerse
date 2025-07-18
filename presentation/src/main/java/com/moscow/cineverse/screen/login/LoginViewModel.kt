package com.moscow.cineverse.screen.login

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.android.domain.model.LoginData
import com.android.domain.usecase.login.LoginAsGuestUseCase
import com.android.domain.usecase.login.LoginWithUsernameAndPasswordUseCase
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cinverse.presentation.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(
    private val context: Context,
    private val loginWithUsernameAndPasswordUseCase: LoginWithUsernameAndPasswordUseCase,
    private val loginAsGuestUseCase: LoginAsGuestUseCase
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
            val isValid =
                trimmedUsername.length in 4..32
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
        if (uiState.value.username.isNotBlank() ||
            uiState.value.password.isNotBlank()
        ) {
            launchWithResult(
                action = {
                    loginWithUsernameAndPasswordUseCase.invoke(
                        loginData = LoginData(
                            username = uiState.value.username,
                            password = uiState.value.password
                        )
                    )
                },
                onSuccess = ::onLoginSuccess,
                onError = ::onLoginFailed,
                onStart = ::onStartLogin,
            )
        }
    }

    private fun onLoginSuccess(isSuccess: Boolean) {
        updateState { it.copy(isLoading = false) }
        if (isSuccess){
            sendEvent(LoginScreenEvents.NavigateToExplore)
        }else{
            sendEvent(LoginScreenEvents.ShowError(context.getString(R.string.sorry_we_cannot_check_your_information_now)))
        }
    }

    private fun onLoginFailed(error: Throwable) {
        updateState { it.copy(isLoading = false) }
        sendEvent(LoginScreenEvents.ShowError(error.message.toString()))
    }

    private fun onStartLogin() {
        updateState { it.copy(isLoading = true) }
    }

    override fun onClickJoinAsGuest() {
        launchWithResult(
            action = { loginAsGuestUseCase.invoke() },
            onSuccess = ::onJoinAsGuestSuccess,
            onError = ::onJoinAsGuestFailed,
        )
    }


    private fun onJoinAsGuestSuccess(isSuccess: Boolean){
        if (isSuccess){
            sendEvent(LoginScreenEvents.NavigateToExplore)
        }else{
            sendEvent(LoginScreenEvents.ShowError(context.getString(R.string.sorry_you_cannot_enter_as_a_guest_now_try_to_create_a_new_account)))
        }
    }

    private fun onJoinAsGuestFailed(error: Throwable){
        sendEvent(LoginScreenEvents.ShowError(error.message.toString()))
    }

    override fun onClickCreateNewAccount() {
        TODO("Not yet implemented")
    }

}