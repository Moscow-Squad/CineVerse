package com.moscow.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.moscow.data_source.theme.ThemeProvider
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ThemeProviderImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ThemeProvider {

    @Volatile
    private var cachedIsDark: Boolean = DEFAULT_IS_DARK

    override suspend fun initializeTheme() {
        val prefs = dataStore.data.first()
        cachedIsDark = prefs[DARK_THEME_KEY] ?: DEFAULT_IS_DARK
    }

    override suspend fun changeAppTheme(isDark: Boolean) {
        dataStore.edit { it[DARK_THEME_KEY] = isDark }
        cachedIsDark = isDark
    }

    override fun getCachedTheme(): Boolean = cachedIsDark

    override suspend fun clearTheme() {
        dataStore.edit { it.remove(DARK_THEME_KEY) }
        cachedIsDark = DEFAULT_IS_DARK
    }

    companion object {
        private val DARK_THEME_KEY = booleanPreferencesKey("is_dark_theme")
        private const val DEFAULT_IS_DARK = true
    }
}
