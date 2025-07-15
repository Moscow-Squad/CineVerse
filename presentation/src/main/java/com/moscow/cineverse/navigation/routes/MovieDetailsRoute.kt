package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsRoute(val movieId: Int)

fun NavGraphBuilder.MovieDetailsRoute(navController: NavHostController) {
    composable<MovieDetailsRoute>{
        val args = it.toRoute<MovieDetailsRoute>()
    }
}