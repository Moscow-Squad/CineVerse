package com.moscow.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.moscow.domain.repository.language.LanguageProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LanguageProviderImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LanguageProvider {

    override val languageFlow: Flow<String> = dataStore.data
        .map { prefs -> prefs[LANGUAGE_KEY] ?: DEFAULT_LANGUAGE }
        .distinctUntilChanged()

    override suspend fun initializeLanguage(deviceLanguage: String) {
        val prefs = dataStore.data.first()
        if (!prefs.contains(LANGUAGE_KEY)) {
            val defaultLang = if (deviceLanguage.startsWith("ar")) "ar" else "en"
            setLanguage(defaultLang)
        }
    }

    override suspend fun setLanguage(language: String) {
        dataStore.edit { it[LANGUAGE_KEY] = language }
    }

    override suspend fun clearLanguageSettings() {
        dataStore.edit { it.remove(LANGUAGE_KEY) }
    }

    companion object {
        private val LANGUAGE_KEY = stringPreferencesKey("app_language")
        private const val DEFAULT_LANGUAGE = "ar"
    }
}
