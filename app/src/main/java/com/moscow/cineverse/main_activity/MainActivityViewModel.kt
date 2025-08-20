package com.moscow.cineverse.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.NavigationDecider
import com.moscow.cineverse.navigation.AppDestination
import com.moscow.domain.service.language.LanguageProvider
import com.moscow.domain.service.theme.ThemeProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val themeProvider: ThemeProvider,
    private val languageProvider: LanguageProvider,
    private val navigationDecider: NavigationDecider
) : ViewModel() {

    private val _state = MutableStateFlow(MainActivityUiState())
    val state = _state.asStateFlow()

    init {
        _state.update { it.copy(language = languageProvider.currentLanguage.value) }

        val precomputed = getPrecomputedDestination()
        if (precomputed != null) {
            _state.update { it.copy(startDestination = precomputed, isLoading = false) }

            verifyDestinationInBackground()
        } else {
            viewModelScope.launch(IO + SupervisorJob()) {
                determineDestination()
            }
        }

        viewModelScope.launch(IO) {
            themeProvider.themeFlow.collect { isDarkTheme ->
                _state.update { it.copy(isDarkTheme = isDarkTheme) }
            }
        }
    }

    private suspend fun determineDestination() = withContext(IO) {
        val destination = navigationDecider.determineDestination()
        _state.update { it.copy(startDestination = destination, isLoading = false) }
    }

    private fun verifyDestinationInBackground() {
        viewModelScope.launch(IO) {
            val correctDestination = navigationDecider.determineDestination()
            if (correctDestination != state.value.startDestination) {
                _state.update { it.copy(startDestination = correctDestination) }
            }
        }
    }

    companion object {
        private var _precomputedDestination: AppDestination? = null

        fun setPrecomputedDestination(destination: AppDestination) {
            _precomputedDestination = destination
        }

        fun getPrecomputedDestination(): AppDestination? {
            return _precomputedDestination
        }
    }
}