package com.moscow.cineverse.navigation.routes

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moscow.cineverse.screen.series_details.SeriesDetailsViewModel
import com.moscow.cineverse.screen.series_details.series_recommendation.SeriesRecommendationScreen
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data class SeriesRecommendationRoute(val seriesId: Int)

fun NavGraphBuilder.SeriesRecommendationRoute() {
    composable<SeriesRecommendationRoute>{
        val viewModel: SeriesDetailsViewModel = koinViewModel()
        SeriesRecommendationScreen(viewModel = viewModel)
    }
}