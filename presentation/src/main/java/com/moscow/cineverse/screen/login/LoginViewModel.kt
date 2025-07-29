package com.moscow.cineverse.screen.login

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cinverse.presentation.R
import com.moscow.domain.model.LoginData
import com.moscow.domain.usecase.login.LoginAsGuestUseCase
import com.moscow.domain.usecase.login.LoginWithUsernameAndPasswordUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val loginWithUsernameAndPasswordUseCase: LoginWithUsernameAndPasswordUseCase,
    private val loginAsGuestUseCase: LoginAsGuestUseCase
) : BaseViewModel<LoginScreenState, LoginScreenEvents>(LoginScreenState()),
    LoginInteractionListener {

    private var usernameValidationJob: Job? = null
    private var passwordValidationJob: Job? = null

    override fun onUsernameValueChanged(username: String) {
        updateState { it.copy(username = username) }
        usernameValidationJob?.cancel()

        usernameValidationJob = validateInputWithDelay(
            input = username,
            isValid = { it.length in 4..32 && it.all { char -> char.isLetterOrDigit() || char == '_' } },
            onResult = { error ->
                updateState { it.copy(usernameError = error) }
            },
            errorMessage = context.getString(R.string.usernames_can_only_4_32_letters_and_numbers)
        )
    }

    override fun onPasswordValueChanged(password: String) {
        updateState { it.copy(password = password) }
        passwordValidationJob?.cancel()

        passwordValidationJob = validateInputWithDelay(
            input = password,
            isValid = { it.length in 4..100 },
            onResult = { error ->
                updateState { it.copy(passwordError = error) }
            },
            errorMessage = context.getString(R.string.password_can_only_4_100_characters)
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
        if (isSuccess){
            sendEvent(LoginScreenEvents.NavigateTo)
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


    private fun onJoinAsGuestSuccess(isSuccess: Boolean) {
        if (isSuccess) {
            sendEvent(LoginScreenEvents.NavigateTo)
        } else {
            sendEvent(LoginScreenEvents.ShowError(context.getString(R.string.sorry_you_cannot_enter_as_a_guest_now_try_to_create_a_new_account)))
        }
    }

    private fun onJoinAsGuestFailed(error: Throwable) {
        sendEvent(LoginScreenEvents.ShowError(error.message.toString()))
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
        updateState { it.copy(
            urlWebView = FORGET_PASSWORD_URL,
            showWebView = true
        ) }
    }

    override fun onExitWebViewBrowser() {
        updateState { it.copy(showWebView = false) }
    }

    private fun validateInputWithDelay(
        input: String,
        delayMillis: Long = 500,
        isValid: (String) -> Boolean,
        onResult: (String?) -> Unit,
        errorMessage: String
    ): Job {
        return viewModelScope.launch {
            delay(delayMillis)
            val trimmedInput = input.trim()
            val isInputValid = isValid(trimmedInput)

            val error = if (!isInputValid && trimmedInput.isNotEmpty()) errorMessage else null
            onResult(error)
        }
    }

    companion object{
        private const val SIGN_UP_URL = "https://www.themoviedb.org/signup"
        private const val FORGET_PASSWORD_URL = "https://www.themoviedb.org/reset-password"
    }

}