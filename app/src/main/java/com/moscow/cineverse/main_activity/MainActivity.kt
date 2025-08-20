package com.moscow.cineverse.main_activity

import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.CineVerseRoot
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
        )

        val viewModel: MainActivityViewModel by viewModels()

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            !viewModel.state.value.isLoading
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()

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

                if (!state.isLoading) {
                    CineVerseRoot(state.startDestination)
                }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return ev?.pointerCount == 1 && super.dispatchTouchEvent(ev)
    }
}