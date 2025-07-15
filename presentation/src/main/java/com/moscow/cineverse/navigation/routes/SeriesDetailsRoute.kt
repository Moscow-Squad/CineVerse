package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class SeriesDetailsRoute(val seriesId: Int)

fun NavGraphBuilder.SeriesDetailsRoute(navController: NavHostController) {
    composable<SeriesDetailsRoute>{
        val args = it.toRoute<SeriesDetailsRoute>()
    }
}