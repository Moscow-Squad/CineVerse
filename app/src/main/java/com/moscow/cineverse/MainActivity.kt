package com.moscow.cineverse


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.screen.component.bottomNavigationBar.BottomNavItem
import com.moscow.cineverse.navigation.CineVerseNavGraph
import com.moscow.cineverse.screen.component.bottomNavigationBar.NavBar
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private lateinit var analytics: FirebaseAnalytics
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analytics = Firebase.analytics
        enableEdgeToEdge()

        setContent {
            CineVerseTheme {
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val selectedDestinationIndex by remember(currentBackStackEntry) {
                    derivedStateOf {
                        BottomNavItem.destinations.indexOfFirst { item ->
                            currentBackStackEntry?.destination?.hasRoute(item.destination::class) == true
                        }.coerceAtLeast(0)
                    }
                }
                val scope = rememberCoroutineScope()
                Scaffold(
                    bottomBar = {
                        NavBar(
                            selectedItem = BottomNavItem.destinations[selectedDestinationIndex],
                            onItemClick = { destination ->
                                scope.launch {
                                    val navOptions = NavOptions.Builder()
                                        .setPopUpTo(
                                            navController.graph.startDestinationId,
                                            inclusive = false
                                        )
                                        .setLaunchSingleTop(true)
                                        .build()
                                    navController.navigate(destination, navOptions)
                                }
                            }
                        )
                    }
                ) {
                    CineVerseNavGraph(navController = navController)
                }
            }
        }
    }
}




