package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class CastGalleryRoute(val castId: Int)

fun NavGraphBuilder.CastGalleryRoute(navController: NavHostController) {
    composable<CastGalleryRoute>{
        val args = it.toRoute<CastGalleryRoute>()
    }
}