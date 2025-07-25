package com.moscow.cineverse.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy

@Composable
fun rememberIsInGraph(
    navBackStackEntry: NavBackStackEntry?,
    graphRoutes: Set<AppDestination>
): State<Boolean> {
    return remember(navBackStackEntry) {
        derivedStateOf {
            val currentHierarchy = navBackStackEntry?.destination?.hierarchy
            graphRoutes.any { graph ->
                currentHierarchy?.any { destination ->
                    destination.route == graph::class.qualifiedName
                } ?: false
            }
        }
    }
}