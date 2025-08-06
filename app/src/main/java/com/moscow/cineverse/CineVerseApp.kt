package com.moscow.cineverse

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.moscow.domain.repository.language.LanguageProvider
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltAndroidApp
class CineVerseApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var languageProvider: LanguageProvider

    override fun onCreate() {
        super.onCreate()
        initializeAppLanguage()
    }

    private fun initializeAppLanguage() {
        val deviceLanguage = Locale.getDefault().language
        CoroutineScope(Dispatchers.Default).launch {
            languageProvider.initializeLanguage(deviceLanguage)
        }    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.DEBUG)
            .build()
}
