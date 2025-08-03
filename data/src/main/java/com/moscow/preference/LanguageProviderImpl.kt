package com.moscow.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.moscow.data_source.language.LanguageProvider
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LanguageProviderImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LanguageProvider {

    @Volatile
    private var cachedLanguage: String = DEFAULT_LANGUAGE

    override suspend fun initializeLanguage(deviceLanguage: String) {
        val prefs = dataStore.data.first()
        if (!prefs.contains(LANGUAGE_KEY)) {
            val defaultLang = if (deviceLanguage.startsWith("ar")) "ar" else "en"
            setLanguageInternal(defaultLang)
        } else {
            cachedLanguage = prefs[LANGUAGE_KEY] ?: DEFAULT_LANGUAGE
        }
    }

    override suspend fun getCurrentLanguage(): String {
        val lang = dataStore.data.first()[LANGUAGE_KEY] ?: DEFAULT_LANGUAGE
        cachedLanguage = lang
        return lang
    }

    override suspend fun setLanguage(language: String) {
        setLanguageInternal(language)
    }

    override fun getCachedLanguage(): String = cachedLanguage

    private suspend fun setLanguageInternal(language: String) {
        dataStore.edit { it[LANGUAGE_KEY] = language }
        cachedLanguage = language
    }

    override suspend fun clearLanguageSettings() {
        dataStore.edit { it.clear() }
    }

    companion object {
        private val LANGUAGE_KEY = stringPreferencesKey("app_language")
        private const val DEFAULT_LANGUAGE = "ar"
    }
}
