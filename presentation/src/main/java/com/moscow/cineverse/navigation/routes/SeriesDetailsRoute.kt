package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.screen.series_details.SeriesDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class SeriesDetailsRoute(val seriesId: Int) {
    companion object {
        const val SERIES_ID = "seriesId"
    }
}

fun NavGraphBuilder.SeriesDetailsRoute(navController: NavHostController) {
    composable<SeriesDetailsRoute>{
        SeriesDetailsScreen(
            navController = navController,
        )
    }
}