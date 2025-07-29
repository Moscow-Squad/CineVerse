package com.moscow.cineverse.screen.login

import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.utlis.StringValue
import com.moscow.cinverse.presentation.R
import com.moscow.domain.model.LoginData
import com.moscow.domain.usecase.login.LoginAsGuestUseCase
import com.moscow.domain.usecase.login.LoginWithUsernameAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginWithUsernameAndPasswordUseCase: LoginWithUsernameAndPasswordUseCase,
    private val loginAsGuestUseCase: LoginAsGuestUseCase
) : BaseViewModel<LoginScreenState, LoginScreenEvents>(LoginScreenState()),
    LoginInteractionListener {

    private var usernameValidationJob: Job? = null
    private var passwordValidationJob: Job? = null

    override fun onUsernameValueChanged(username: String) {
        if (username.length <= 33) {
            updateState { it.copy(username = username) }
        }
        usernameValidationJob?.cancel()

        usernameValidationJob = validateInput(
            input = username,
            isPassword = false,
            onResult = { error ->
                updateState { it.copy(usernameError = error) }
            }
        )
    }

    override fun onPasswordValueChanged(password: String) {
        if(password.length <= 100){
            updateState { it.copy(password = password) }
        }
        passwordValidationJob?.cancel()

        passwordValidationJob = validateInput(
            input = password,
            isPassword = true,
            onResult = { error ->
                updateState { it.copy(passwordError = error) }
            }
        )
    }

    override fun onClickLogin() {
        if (uiState.value.username.isNotBlank() &&
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
        if (isSuccess) {
            sendEvent(LoginScreenEvents.NavigateTo)
        } else {
            sendEvent(LoginScreenEvents.ShowError(StringValue.StringResource(resId = R.string.sorry_we_cannot_check_your_information_now)))
        }
    }

    private fun onLoginFailed(error: Throwable) {
        updateState {
            it.copy(
                isLoading = false,
                passwordError = StringValue.StringResource(resId = R.string.incorrect_username_or_password_please_try_again)
            ) }
    }

    private fun onStartLogin() {
        updateState { it.copy(isLoading = true) }
    }

    override fun onClickJoinAsGuest() {
        updateState { it.copy(isJoinAsGuest = true) }
        launchWithResult(
            action = { loginAsGuestUseCase.invoke() },
            onSuccess = ::onJoinAsGuestSuccess,
            onError = ::onJoinAsGuestFailed,
        )
    }


    private fun onJoinAsGuestSuccess(isSuccess: Boolean) {
        updateState { it.copy(isJoinAsGuest = false) }
        if (isSuccess) {
            sendEvent(LoginScreenEvents.NavigateTo)
        } else {
            sendEvent(LoginScreenEvents.ShowError(StringValue.StringResource(resId = R.string.sorry_you_cannot_enter_as_a_guest_now_try_to_create_a_new_account)))
        }
    }

    private fun onJoinAsGuestFailed(error: Throwable) {
        sendEvent(LoginScreenEvents.ShowError(StringValue.StringResource(resId = R.string.oops_no_internet)))
    }

    override fun onClickCreateNewAccount() {
        updateState { it.copy(showSignUpBottomSheet = true) }
    }

    override fun onDismissOrCancelSignUpBottomSheet() {
        updateState { it.copy(showSignUpBottomSheet = false) }
    }

    override fun onClickGoToWebsite() {
        updateState {
            it.copy(
                showSignUpBottomSheet = false,
                urlWebView = SIGN_UP_URL,
                showWebView = true
            )
        }
    }

    override fun onClickForgetPassword() {
        updateState {
            it.copy(
                urlWebView = FORGET_PASSWORD_URL,
                showWebView = true
            )
        }
    }

    override fun onExitWebViewBrowser() {
        updateState { it.copy(showWebView = false) }
    }

    private fun validateInput(
        input: String,
        isPassword: Boolean,
        delayMillis: Long = 100,
        onResult: (StringValue?) -> Unit
    ): Job {
        return viewModelScope.launch {
            delay(delayMillis)

            val trimmed = input.trim()

            val error = when {
                input.isEmpty() -> null

                input != trimmed -> {
                    StringValue.StringResource(R.string.no_leading_trailing_spaces)
                }

                !isPassword && trimmed.length !in 4..32 -> {
                    StringValue.StringResource(R.string.username_length_invalid)
                }

                isPassword && trimmed.length !in 4..100 -> {
                    StringValue.StringResource(R.string.password_can_only_4_100_characters)
                }

                !isPassword && !trimmed.all { it.isLetterOrDigit() || it == '_' || it == ' ' } -> {
                    StringValue.StringResource(R.string.username_invalid_chars)
                }

                else -> null
            }

            onResult(error)
        }
    }

    companion object {
        private const val SIGN_UP_URL = "https://www.themoviedb.org/signup"
        private const val FORGET_PASSWORD_URL = "https://www.themoviedb.org/reset-password"
    }

}