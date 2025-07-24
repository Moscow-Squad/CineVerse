package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moscow.cineverse.navigation.AppDestination
import com.moscow.cineverse.screen.explore.ExploreScreen
import kotlinx.serialization.Serializable

@Serializable
object ExploreRoute : AppDestination

fun NavGraphBuilder.exploreRoute() {
    composable<ExploreRoute>{
        ExploreScreen()
    }
}