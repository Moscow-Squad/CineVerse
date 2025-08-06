package com.moscow.cineverse

import androidx.lifecycle.ViewModel
import com.moscow.domain.repository.theme.ThemeProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.moscow.domain.repository.language.LanguageProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val themeProvider: ThemeProvider,

): ViewModel() {

    private val _state = MutableStateFlow(MainActivityUiState())
    val state = _state.asStateFlow()

    init {
        loadScreen()
    }

    private fun loadScreen() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            themeProvider.themeFlow.collect { isDarkTheme ->
                _state.update {
                    it.copy(
                        isDarkTheme = isDarkTheme,
                        isLoading = false
                    )
                }
            }

        }
    }

}