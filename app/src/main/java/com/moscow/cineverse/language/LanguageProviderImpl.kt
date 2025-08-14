package com.moscow.cineverse.language

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.LocaleManagerCompat
import androidx.core.os.LocaleListCompat
import com.moscow.domain.service.language.LanguageProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LanguageManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LanguageProvider {

    override suspend fun setLanguage(language: String) {
        val languageCode = when (language) {
            AppLanguage.English.code -> "en"
            AppLanguage.Arabic.code -> "ar"
            else -> "en"
        }
        val appLocale = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    override fun getCurrentLanguage(): String = run {
        val systemLanguage = LocaleManagerCompat.getSystemLocales(context)[0]
        val appLanguage = AppCompatDelegate.getApplicationLocales()[0] ?: systemLanguage
        appLanguage?.language ?: "en"
    }
}