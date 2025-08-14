package com.moscow.cineverse.screen.profile

import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.domain.model.UserType
import com.moscow.domain.model.UserInfo
import com.moscow.domain.service.blur.BlurProvider
import com.moscow.domain.service.language.LanguageProvider
import com.moscow.domain.service.theme.ThemeProvider
import com.moscow.domain.usecase.preference.GetUserDetailsUseCase
import com.moscow.domain.usecase.preference.RemoveUserDetailsUseCase
import com.moscow.domain.usecase.profile.GetAccountDetailsUseCase
import com.moscow.domain.usecase.profile.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val removeUserDetailsUseCase: RemoveUserDetailsUseCase,
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase,
    private val themeProvider: ThemeProvider,
    private val languageProvider: LanguageProvider,
    private val blurProvider: BlurProvider
) : BaseViewModel<ProfileUIState, ProfileScreenEffects>(ProfileUIState()),
    ProfileInteractionListener {

    init {
        getCurrentLanguage()
        observeTheme()
        observeBlur()
        getUserDetails()
    }

    private fun getCurrentLanguage() {
        updateState { it.copy(currentLanguage = languageProvider.getCurrentLanguage()) }
    }

    private fun observeTheme() {
        viewModelScope.launch {
            themeProvider.themeFlow.collect { isDark ->
                updateState { it.copy(isDarkTheme = isDark) }
            }
        }
    }

    private fun observeBlur() {
        viewModelScope.launch {
            blurProvider.blurFlow.collect { enableBlur ->
                updateState { it.copy(selectedPreference = enableBlur) }
            }
        }
    }

    private fun getAccountDetails() {
        launchWithResult(
            action = { getAccountDetailsUseCase(uiState.value.accountId, uiState.value.sessionId) },
            onStart = ::onLoading,
            onSuccess = ::onGetAccountDetailsSuccess,
            onError = { e ->

            }
        )
    }

    private fun onGetAccountDetailsSuccess(userInfo: UserInfo) {

        updateState {
            it.copy(
                name = userInfo.name,
                username = userInfo.username,
                image = userInfo.image,
            )

        }

    }

    private fun logout() {
        launchWithResult(
            action = { logoutUseCase(sessionId = uiState.value.sessionId) },
            onSuccess = ::onGetLogoutSuccess,
            onError = { onLogoutFailed() })
    }

    private fun onGetLogoutSuccess(success: Boolean) {
        if (success) {
            removeUserDetails()
            updateState { ProfileUIState() }
        }
        onCancelLogoutBottomSheet()
    }

    private fun onLogoutFailed() {
        onCancelLogoutBottomSheet()
        sendEvent(ProfileScreenEffects.OnLogoutFailed)
    }

    fun removeUserDetails() {
        launchWithResult(
            action = { removeUserDetailsUseCase() },
            onSuccess = { sendEvent(ProfileScreenEffects.OnLogoutSuccessfully) },
            onError = {})
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

    fun updateAppBlur(enable: String) {
        viewModelScope.launch {
            blurProvider.changeBlur(enabled = enable)
        }
    }

    fun getUserDetails() {
        launchWithResult(
            action = { getUserDetailsUseCase() },
            onSuccess = ::onGetUserDetailsSuccess,
            onError = {})
    }

    private fun onGetUserDetailsSuccess(userType: UserType) {
        when (userType) {
            is UserType.AuthenticatedUser -> {
                updateState {
                    it.copy(
                        name = userType.name,
                        username = userType.username,
                        image = userType.image,
                        sessionId = userType.sessionId,
                        accountId = userType.id

                    )
                }
            }

            is UserType.GuestUser -> {
                updateState {
                    it.copy(
                        isGuest = true
                    )
                }
            }
        }

    }

    private fun onLoading() {
        updateState { it.copy(isLoading = true, errorMessage = null) }
    }

    override fun onShowEditProfileBottomSheet() {
        updateState { it.copy(showEditProfileBottomSheet = true) }
    }

    override fun onShowLogoutBottomSheet() {
        updateState { it.copy(showLogoutBottomSheet = true) }
    }

    override fun onShowLanguageBottomSheet() {
        updateState { it.copy(showLanguageBottomSheet = true) }
    }

    override fun onShowPreferencesBottomSheet() {
        updateState { it.copy(showPreferencesBottomSheet = true) }
    }

    override fun onClickEditProfile() {
        val username = uiState.value.username.orEmpty()
        sendEvent(ProfileScreenEffects.GoToWebView(EDIT_PROFILE_URL + username))
        onCancelEditProfileBottomSheet()
    }

    override fun onClickLogout() {
        logout()
    }

    override fun onClickLogin() {
        sendEvent(ProfileScreenEffects.OnLoginClick)
        updateState { ProfileUIState() }
    }

    override fun onSelectedPreference(enable: String) {
        updateAppBlur(enable = enable)
    }

    override fun onExitWebView() {
        getAccountDetails()
        updateState { it.copy(goToWebView = false) }
    }

    override fun onSelectedLanguage(language: String) {
        updateState { it.copy(currentLanguage = language) }
        updateAppLanguage(language)
    }

    override fun onCancelLanguageBottomSheet() {
        updateState { it.copy(showLanguageBottomSheet = false) }
    }

    override fun onCancelEditProfileBottomSheet() {
        updateState { it.copy(showEditProfileBottomSheet = false) }
    }

    override fun onCancelLogoutBottomSheet() {
        updateState { it.copy(showLogoutBottomSheet = false) }
    }

    override fun onCancelPreferencesBottomSheet() {
        updateState { it.copy(showPreferencesBottomSheet = false) }
    }

    override fun onClickMyRatings() {
        sendEvent(ProfileScreenEffects.NavigateToMyRating)
    }

    override fun onClickMyCollections() {
        sendEvent(ProfileScreenEffects.NavigateToMyCollections)

    }

    override fun onClickHistory() {
        sendEvent(ProfileScreenEffects.NavigateToHistory)

    }

    companion object {
        const val EDIT_PROFILE_URL = "https://www.themoviedb.org/u/"
    }

}