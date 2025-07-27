package com.moscow.cineverse.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy

@Composable
fun rememberNavGraphIndex(
    navBackStackEntry: NavBackStackEntry?,
    graphRoutes: Set<AppDestination>
): State<Int> {
    return remember(navBackStackEntry) {
        derivedStateOf {
            val currentRoute = navBackStackEntry?.destination?.route

            graphRoutes.forEachIndexed { index, destination ->
                if (currentRoute == destination::class.qualifiedName) {
                    return@derivedStateOf index
                }
            }

            val currentHierarchy = navBackStackEntry?.destination?.hierarchy
            val index = graphRoutes.indexOfFirst { graph ->
                currentHierarchy?.any { destination ->
                    destination.route == graph::class.qualifiedName
                } ?: false
            }

            if (index >= 0) index else 0
        }
    }
}