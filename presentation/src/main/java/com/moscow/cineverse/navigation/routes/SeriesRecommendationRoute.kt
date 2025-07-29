package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.screen.series_details.series_recommendation.SeriesRecommendationScreen
import kotlinx.serialization.Serializable

@Serializable
data class SeriesRecommendationRoute(val seriesId: Int, val seriesName: String) {
    companion object {
        const val SERIES_ID = "seriesId"
        const val SERIES_NAME = "seriesName"
    }
}

fun NavGraphBuilder.SeriesRecommendationRoute(navController: NavHostController) {
    composable<SeriesRecommendationRoute>{
        SeriesRecommendationScreen(
            navigateBack = { navController.navigateUp() }
        )
    }
}