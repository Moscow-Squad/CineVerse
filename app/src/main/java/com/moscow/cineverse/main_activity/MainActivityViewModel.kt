package com.moscow.cineverse.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.navigation.routes.HomeRoute
import com.moscow.cineverse.navigation.routes.LoginRoute
import com.moscow.cineverse.navigation.routes.OnBoardingRoute
import com.moscow.domain.repository.OnboardingRepository
import com.moscow.domain.repository.auth.UserRepository
import com.moscow.domain.service.language.LanguageProvider
import com.moscow.domain.service.theme.ThemeProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val themeProvider: ThemeProvider,
    private val languageProvider: LanguageProvider,
    private val onboardingRepository: OnboardingRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MainActivityUiState())
    val state = _state.asStateFlow()

    init {
        loadCriticalNavigationData()

        viewModelScope.launch(IO) {
            loadUiPreferences()
        }
    }

    private fun loadCriticalNavigationData() {
        viewModelScope.launch(IO) {
            val isOnboardingCompletedDeferred = async { onboardingRepository.isOnBoardingCompleted() }
            val isGuestDeferred = async { userRepository.isGuest() }
            val isLoggedInDeferred = async { userRepository.isLoggedIn() }

            val isOnboardingCompleted = isOnboardingCompletedDeferred.await()
            val isGuest = isGuestDeferred.await()
            val isLoggedIn = isLoggedInDeferred.await()

            val startDestination = when {
                !isOnboardingCompleted -> OnBoardingRoute
                isGuest -> {
                    val sessionExpiration = userRepository.getSessionExpiration()
                    if (isValidGuestSession(sessionExpiration)) HomeRoute else LoginRoute
                }
                isLoggedIn -> HomeRoute
                else -> LoginRoute
            }

            _state.update { it.copy(startDestination = startDestination, isLoading = false) }
        }
    }

    private fun loadUiPreferences() {
        viewModelScope.launch(IO) {
            themeProvider.themeFlow.collect { isDarkTheme ->
                _state.update { it.copy(isDarkTheme = isDarkTheme) }
            }
        }

        val currentLanguage = languageProvider.currentLanguage.value
        _state.update { it.copy(language = currentLanguage) }
    }

    private fun isValidGuestSession(expirationDateTime: String): Boolean {
        if (expirationDateTime.isEmpty()) return false

        val iso = expirationDateTime.replace(" UTC", "").replace(' ', 'T') + "Z"
        return runCatching {
            val expiresAtInstant = Instant.parse(iso)
            val expiresAtMillis = expiresAtInstant.toEpochMilliseconds()
            val now = Clock.System.now().toEpochMilliseconds()
            now < expiresAtMillis
        }.getOrDefault(false)
    }
}