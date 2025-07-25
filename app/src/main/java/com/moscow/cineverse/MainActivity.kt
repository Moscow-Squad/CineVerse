package com.moscow.cineverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.navigation.CineVerseNavGraph
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.moscow.cineverse.navigation.NavViewModel
import com.moscow.cineverse.navigation.navigateToNewGraph
import com.moscow.cineverse.navigation.rememberIsInGraph
import com.moscow.cineverse.navigation.rememberNavGraphIndex
import com.moscow.cineverse.screen.component.bottomNavigationBar.BottomNavItem.Companion.destinations
import com.moscow.cineverse.screen.component.bottomNavigationBar.NavBar
import org.koin.androidx.viewmodel.ext.android.viewModel
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
            CineVerseTheme {
                val navController = rememberNavController()

                val currentNavBackStackEntry by navController.currentBackStackEntryAsState()

                val navGraphIndex by rememberNavGraphIndex(currentNavBackStackEntry, destinations.keys)

                val isNavBarVisible by rememberIsInGraph(currentNavBackStackEntry, destinations.keys)

                Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding(),
                bottomBar = {
                    AnimatedVisibility(
                        visible = isNavBarVisible
                    ) {
                        NavBar(
                            selectedItem = destinations.values.elementAt(navGraphIndex),
                            onItemClick = { index, _ ->
                                val targetGraph = destinations.keys.elementAt(index)
                                navController.navigateToNewGraph(targetGraph)
                            }
                        )
                    }
                }
                ) {
                    CineVerseNavGraph( modifier = Modifier,
                        navController = navController, navViewModel = navViewModel)
            }
            }
            CineVerseRoot()
        }
    }
}

