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
        getStartDestination()
        observeTheme()
        observeLanguage()
    }


    private fun getStartDestination() {
        viewModelScope.launch(IO) {
            if (!onboardingRepository.isOnBoardingCompleted()) {
                _state.update { it.copy(startDestination = OnBoardingRoute, isLoading = false) }
            } else if (userRepository.isGuest() && userRepository.isLoggedIn()) {
                val isValid = isValidGuestSession(userRepository.getSessionExpiration())
                _state.update { it.copy(startDestination = if (isValid) HomeRoute else LoginRoute, isLoading = false) }
            } else {
                val isLoggedIn = userRepository.isLoggedIn()
                _state.update { it.copy(startDestination = if (isLoggedIn) HomeRoute else LoginRoute, isLoading = false) }
            }
        }
    }

    private fun isValidGuestSession(expirationDateTime: String): Boolean {
        if (expirationDateTime.isNotEmpty()) {
            val iso = expirationDateTime.replace(" UTC", "").replace(' ', 'T') + "Z"
            val expiresAtInstant = Instant.parse(iso)
            val expiresAtMillis = expiresAtInstant.toEpochMilliseconds()
            val now = Clock.System.now().toEpochMilliseconds()
            return now < expiresAtMillis
        }
        return false
    }


    private fun observeTheme() {
        viewModelScope.launch(IO) {
            themeProvider.themeFlow.collect { isDarkTheme ->
                _state.update { it.copy(isDarkTheme = isDarkTheme) }
            }
        }
    }

    private fun observeLanguage() {
        _state.update { it.copy(language = languageProvider.currentLanguage.value) }
    }

}
