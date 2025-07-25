package com.moscow

import com.moscow.data_source.system.LanguageProvider
import java.util.Locale

class DeviceLanguageProvider(
    private val localeResolver: () -> Locale = { Locale.getDefault() }
) : LanguageProvider {

    override fun getCurrentLanguage(): String {
        return if (localeResolver().language == "ar") "ar" else "en"
    }
}