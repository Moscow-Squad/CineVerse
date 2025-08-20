package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.navigation.AppDestination
import com.moscow.cineverse.screen.details.series_details.series_recommendation.SeriesRecommendationScreen
import kotlinx.serialization.Serializable

@Serializable
data class SeriesRecommendationRoute(val seriesId: Int, val seriesName: String) : AppDestination {
    companion object {
        const val SERIES_ID = "seriesId"
        const val SERIES_NAME = "seriesName"
    }
}

fun NavGraphBuilder.seriesRecommendationRoute(navController: NavHostController) {
    composable<SeriesRecommendationRoute>{
        SeriesRecommendationScreen(
            navigateBack = { navController.navigateUp() },
            navigateToSeriesDetails = { seriesId ->
                navController.navigate(SeriesDetailsRoute(seriesId))
            }
        )
    }
}