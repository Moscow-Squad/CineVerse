package com.moscow

import com.google.common.truth.Truth.assertThat
import com.moscow.preference.LanguageProviderImpl
import org.junit.jupiter.api.Test
import java.util.Locale

class DeviceLanguageProviderTest {

    @Test
    fun `returns ar when locale is Arabic`() {
        val provider = LanguageProviderImpl { Locale("ar") }

        val language = provider.getCurrentLanguage()

        assertThat(language).isEqualTo("ar")
    }

    @Test
    fun `returns en when locale is not Arabic`() {
        val provider = LanguageProviderImpl { Locale("fr") }

        val language = provider.getCurrentLanguage()

        assertThat(language).isEqualTo("en")
    }

    @Test
    fun `returns en when locale is default and not Arabic`() {
        val provider = LanguageProviderImpl { Locale.ENGLISH }

        val language = provider.getCurrentLanguage()

        assertThat(language).isEqualTo("en")
    }
}