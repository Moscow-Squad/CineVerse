package com.moscow.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.moscow.domain.repository.language.LanguageProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class LanguageProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStore: DataStore<Preferences>
) : LanguageProvider {

    private val _languageState = MutableStateFlow(DEFAULT_LANGUAGE)
    override val languageFlow: StateFlow<String> = _languageState

    private var cachedLanguage: String = DEFAULT_LANGUAGE

    init {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.data
                .map { it[LANGUAGE_KEY] ?: DEFAULT_LANGUAGE }
                .distinctUntilChanged()
                .collect {
                    cachedLanguage = it
                    _languageState.value = it
                }
        }
    }

    override suspend fun initializeLanguage(deviceLanguage: String) {
        val prefs = dataStore.data.first()
        if (!prefs.contains(LANGUAGE_KEY)) {
            val defaultLang = if (deviceLanguage.startsWith("ar")) "ar" else "en"
            setLanguage(defaultLang)
            context.getSharedPreferences("settings", Context.MODE_PRIVATE).edit()
                .putString("app_language", deviceLanguage).apply()
        }
    }

    override suspend fun setLanguage(language: String) {
        dataStore.edit { it[LANGUAGE_KEY] = language }
        context.getSharedPreferences("settings", Context.MODE_PRIVATE).edit()
            .putString("app_language", language).apply()
    }

    override suspend fun clearLanguageSettings() {
        dataStore.edit { it.remove(LANGUAGE_KEY) }
    }

    override fun getCachedLanguage(): String = cachedLanguage

    companion object {
        private val LANGUAGE_KEY = stringPreferencesKey("app_language")
        private const val DEFAULT_LANGUAGE = "ar"
    }
}
