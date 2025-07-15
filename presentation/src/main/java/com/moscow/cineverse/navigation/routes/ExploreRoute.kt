package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moscow.cineverse.screen.explore.ExploreScreen
import kotlinx.serialization.Serializable

@Serializable
object ExploreRoute

fun NavGraphBuilder.ExploreRoute(navController: NavController) {
    composable<ExploreRoute>{
        ExploreScreen(navController = navController)
    }
}