package com.moscow.cineverse.screen.profile

import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.domain.model.profile.AccountDetails
import com.moscow.domain.repository.language.LanguageProvider
import com.moscow.domain.repository.theme.ThemeProvider
import com.moscow.domain.usecase.profile.GetAccountDetailsUseCase
import com.moscow.domain.usecase.profile.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase,
    private val themeProvider: ThemeProvider,
    private val languageProvider: LanguageProvider
) : BaseViewModel<ProfileUIState, ProfileScreenEffects>(ProfileUIState()),
    ProfileInteractionListener {

    init {
        observeTheme()
        observeLanguage()
    }

    private fun observeTheme() {
        viewModelScope.launch {
            themeProvider.themeFlow.collect { isDark ->
                updateState { it.copy(isDarkTheme = isDark) }
            }
        }
    }

    private fun observeLanguage() {
        viewModelScope.launch {
            val deviceLanguage = Locale.getDefault().language
            languageProvider.initializeLanguage(deviceLanguage)
            languageProvider.languageFlow.collect { language ->
                updateState { it.copy(appLanguage = language) }
            }
        }
    }

    fun updateAppTheme(isDark: Boolean) {
        viewModelScope.launch {
            themeProvider.changeAppTheme(isDark = isDark)
        }
    }

    fun updateAppLanguage(language: String) {
        viewModelScope.launch {
            languageProvider.setLanguage(language = language)
        }
    }

    fun getAccountDetails(accountId: String, sessionId: String) {
        launchWithResult(
            action = { getAccountDetailsUseCase(accountId, sessionId) },
            onSuccess = { ::onGetAccountDetailsSuccess },
            onStart = { ::onLoading },
            onError = { ::onGetAccountDetailsFailed }
        )
    }

    private fun onGetAccountDetailsSuccess(accountDetails: AccountDetails) {
        updateState {
            it.copy(
                name = accountDetails.name,
                username = accountDetails.username,
                image = accountDetails.image,
                isLoading = false,
            )
        }
    }

    private fun onGetAccountDetailsFailed(error: Throwable) {
        updateState {
            it.copy(
                isLoading = false,
                errorMessage = error.message
            )
        }
    }

    private fun onLoading() {
        updateState { it.copy(isLoading = true, errorMessage = null) }
    }

    override fun onClickEditProfile() {
        sendEvent(ProfileScreenEffects.navigateToEditProfile)
    }

    override fun onClickLogout() {
        sendEvent(ProfileScreenEffects.navigateToLogout)
    }

}