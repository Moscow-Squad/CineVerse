package com.moscow.cineverse.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moscow.domain.repository.language.LanguageProvider
import com.moscow.domain.repository.theme.ThemeProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.util.Locale

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val themeProvider: ThemeProvider,
    private val languageProvider: LanguageProvider
) : ViewModel() {

    private val _state = MutableStateFlow(MainActivityUiState())
    val state = _state.asStateFlow()

    init {
        initializeLanguage()
        observeProviders()
    }

    private fun initializeLanguage() {
        viewModelScope.launch {
            val deviceLanguage = Locale.getDefault().language
            languageProvider.initializeLanguage(deviceLanguage)
        }
    }

    private fun observeProviders() {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            themeProvider.themeFlow.collect { isDarkTheme ->
                _state.update { it.copy(isDarkTheme = isDarkTheme) }
            }
        }

        viewModelScope.launch {
            languageProvider.languageFlow.collect { lang ->
                _state.update {
                    it.copy(language = lang, isLoading = false)
                }
            }
        }
    }
}
