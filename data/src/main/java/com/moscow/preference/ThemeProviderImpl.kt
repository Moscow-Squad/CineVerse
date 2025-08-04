package com.moscow.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.moscow.domain.repository.theme.ThemeProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThemeProviderImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ThemeProvider {

    override val themeFlow: Flow<Boolean> = dataStore.data
        .map { it[DARK_THEME_KEY] ?: DEFAULT_IS_DARK }
        .distinctUntilChanged()

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
