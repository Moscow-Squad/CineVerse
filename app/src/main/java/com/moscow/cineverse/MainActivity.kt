package com.moscow.cineverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.moscow.cineverse.navigation.NavViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val navViewModel: NavViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            navViewModel.startDestination.value == null
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            CineVerseRoot(navViewModel)
        }
    }
}

