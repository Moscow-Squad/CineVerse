package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.moscow.cineverse.screen.series_details.SeriesDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class SeriesDetailsRoute(val seriesId: Int)

fun NavGraphBuilder.SeriesDetailsRoute() {
    composable<SeriesDetailsRoute>{
        val args = it.toRoute<SeriesDetailsRoute>()
        SeriesDetailsScreen(
            args.seriesId
        )
    }
}