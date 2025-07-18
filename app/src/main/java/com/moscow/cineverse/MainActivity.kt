package com.moscow.cineverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.navigation.CineVerseNavGraph
import com.moscow.cineverse.screen.series_details.SeriesDetailsScreen


class MainActivity : ComponentActivity() {

    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
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