package com.moscow.cineverse.main_activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import com.moscow.cineverse.CineVerseRoot
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.navigation.NavViewModel
import com.moscow.domain.repository.theme.ThemeProvider
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var themeProvider: ThemeProvider

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    private val navViewModel: NavViewModel by viewModels()

    override fun attachBaseContext(newBase: Context) {
        val updatedContext = updateLocale(newBase, getSavedLanguage(newBase))
        super.attachBaseContext(updatedContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.apply {
                hide(WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }

        setContent {
            val state by mainActivityViewModel.state.collectAsState()

            val previousLanguage = remember { mutableStateOf(state.language) }

            LaunchedEffect(state.language) {
                if (previousLanguage.value != state.language) {
                    previousLanguage.value = state.language
                    recreate()
                }
            }

            CineVerseTheme(isDark = state.isDarkTheme) {
                CineVerseRoot(navViewModel)
            }
        }
    }
}

fun getSavedLanguage(context: Context): String {
    val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    return prefs.getString("app_language", "en") ?: "en"
}

fun updateLocale(context: Context, language: String): Context {
    val locale = Locale(language)
    Locale.setDefault(locale)

    val config = context.resources.configuration
    config.setLocale(locale)
    config.setLayoutDirection(locale)

    return context.createConfigurationContext(config)
}
