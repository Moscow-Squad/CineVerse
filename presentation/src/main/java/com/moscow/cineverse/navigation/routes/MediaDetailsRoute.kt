package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class MediaDetailsRoute(val MediaId: Int, val isMovie: Boolean)

fun NavGraphBuilder.MediaDetailsRoute(navController: NavController) {
    composable<MediaDetailsRoute>{
        val args = it.toRoute<MediaDetailsRoute>()
    }
}