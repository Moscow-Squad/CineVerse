package com.moscow.cineverse.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moscow.domain.repository.language.LanguageProvider
import com.moscow.domain.repository.theme.ThemeProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val themeProvider: ThemeProvider,
    private val languageProvider: LanguageProvider
) : ViewModel() {

    private val _state = MutableStateFlow(MainActivityUiState())
    val state = _state.asStateFlow()

    init {
        observeTheme()
        observeLanguage()
    }

    private fun observeLanguage() {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            languageProvider.languageFlow.collect { lang ->
                _state.update {
                    it.copy(language = lang, isLoading = false)
                }
            }
        }
    }

    private fun observeTheme() {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch(Dispatchers.IO) {
            themeProvider.themeFlow.collect { isDarkTheme ->
                _state.update { it.copy(isDarkTheme = isDarkTheme) }
            }
        }
    }
}
