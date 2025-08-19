package com.moscow.cineverse.main_activity

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.CineVerseRoot
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.navigation.NavViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    private val navViewModel: NavViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val state by mainActivityViewModel.state.collectAsStateWithLifecycle()
            AppCompatDelegate.setDefaultNightMode(
                if (state.isDarkTheme) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            CineVerseTheme(language = state.language, isDark = state.isDarkTheme) {
                val surfaceColor = Theme.colors.background.screen.toArgb()

                LaunchedEffect(state.isDarkTheme) {
                    enableEdgeToEdge(
                        statusBarStyle = if (!state.isDarkTheme) {
                            SystemBarStyle.light(
                                scrim = surfaceColor,
                                darkScrim = surfaceColor
                            )
                        } else {
                            SystemBarStyle.dark(scrim = surfaceColor)
                        },
                        navigationBarStyle = if (!state.isDarkTheme) {
                            SystemBarStyle.light(
                                scrim = surfaceColor,
                                darkScrim = surfaceColor
                            )
                        } else {
                            SystemBarStyle.dark(scrim = surfaceColor)
                        }
                    )
                }
                CineVerseRoot(navViewModel)
            }
        }
    }
}