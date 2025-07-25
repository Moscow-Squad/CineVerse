package com.moscow.cineverse

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.navigation.CineVerseNavGraph
import com.moscow.cineverse.screen.component.bottomNavigationBar.BottomNavItem
import com.moscow.cineverse.screen.component.bottomNavigationBar.NavBar
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CineVerseRoot() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val selectedItemIndex by remember(currentBackStackEntry) {
        derivedStateOf {
            BottomNavItem.destinations.indexOfFirst { item ->
                currentBackStackEntry?.destination?.hasRoute(item.destination::class) == true
            }.coerceAtLeast(0)
        }
    }

    CineVerseTheme {
        Scaffold(
            bottomBar = {
                NavBar(
                    selectedItem = BottomNavItem.destinations[selectedItemIndex],
                    onItemClick = { destination ->
                        scope.launch {
                            navController.navigate(
                                route = destination,
                                navOptions = NavOptions.Builder()
                                    .setPopUpTo(
                                        navController.graph.startDestinationId,
                                        inclusive = false
                                    )
                                    .setLaunchSingleTop(true)
                                    .build()
                            )
                        }
                    }
                )
            }
        ) {paddingValues ->
            CineVerseNavGraph(
                navController = navController,
                scaffoldPaddingValues = paddingValues
            )
        }
    }
}
