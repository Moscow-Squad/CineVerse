package com.moscow.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.moscow.domain.service.theme.ThemeProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class ThemeProviderImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ThemeProvider {

    private val _themeState = MutableStateFlow(DEFAULT_IS_DARK)
    override val themeFlow: StateFlow<Boolean> = _themeState

    init {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.data
                .map { it[DARK_THEME_KEY] ?: DEFAULT_IS_DARK }
                .distinctUntilChanged()
                .collect { _themeState.value = it }
        }
    }

    override suspend fun changeAppTheme(isDark: Boolean) {
        dataStore.edit { it[DARK_THEME_KEY] = isDark }
    }

    override suspend fun clearTheme() {
        dataStore.edit { it.remove(DARK_THEME_KEY) }
    }

    companion object {
        private val DARK_THEME_KEY = booleanPreferencesKey("is_dark_theme")
        private const val DEFAULT_IS_DARK = true
    }
}
