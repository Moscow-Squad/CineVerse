package com.moscow.cineverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.navigation.CineVerseNavGraph
import com.moscow.cineverse.navigation.NavViewModel


class MainActivity : ComponentActivity() {

    private lateinit var analytics: FirebaseAnalytics
    private val navViewModel: NavViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            navViewModel.startDestination.value == null
        }

        super.onCreate(savedInstanceState)
        analytics = Firebase.analytics
        enableEdgeToEdge()
        setContent {
            CineVerseTheme {

                CineVerseNavGraph()


            }
        }
    }
}