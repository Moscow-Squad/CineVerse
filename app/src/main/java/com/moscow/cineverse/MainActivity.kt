package com.moscow.cineverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.screen.cast_details_gallery.ActorGalleryScreen
import com.moscow.cineverse.screen.explore.ExploreScreen

class MainActivity : ComponentActivity() {

    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analytics = Firebase.analytics
        enableEdgeToEdge()
        setContent {
            CineVerseTheme {
                ActorGalleryScreen(actorId = 679, title = "nour el-hoda")
            }
        }
    }
}