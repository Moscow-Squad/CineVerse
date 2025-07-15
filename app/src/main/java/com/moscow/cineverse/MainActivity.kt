package com.moscow.cineverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.android.domain.model.Actor
import com.android.domain.model.Gender
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.screen.castDetails.CastDetailsScreen
import com.moscow.cineverse.screen.explore.ExploreScreen

class MainActivity : ComponentActivity() {

    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analytics = Firebase.analytics
        enableEdgeToEdge()
        setContent {
            CineVerseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    // State to track navigation
    var currentScreen by remember { mutableStateOf("main") }
    var selectedActor by remember { mutableStateOf<Actor?>(null) }

    when (currentScreen) {
        "main" -> {
            MainScreen(
                onActorSelected = { actor ->
                    selectedActor = actor
                    currentScreen = "cast_details"
                }
            )
        }
        "cast_details" -> {
            selectedActor?.let { actor ->
                CastDetailsScreen(
                    actor = actor,
                    onNavigateBack = {
                        currentScreen = "main"
                        selectedActor = null
                    }
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    onActorSelected: (Actor) -> Unit
) {
    // Your main screen content here
    // This is just an example - replace with your actual main screen

    // Example: Create a sample actor for testing
    val sampleActor = Actor(
        id = 1,
        name = "Sample Actor",
        profileImg = "https://example.com/profile.jpg",
        gender = Gender.MALE
    )

    // Example button to navigate to cast details
    androidx.compose.material3.Button(
        onClick = { onActorSelected(sampleActor) }
    ) {
        androidx.compose.material3.Text("View Cast Details")
    }
}