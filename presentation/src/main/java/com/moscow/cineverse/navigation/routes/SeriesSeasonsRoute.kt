package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moscow.cineverse.screen.series_details.SeriesDetailsScreenScreenViewModel
import com.moscow.cineverse.screen.series_details.series_seasons.SeriesSeasonsScreen
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data class SeriesSeasonsRoute(val seriesId: Int)

fun NavGraphBuilder.SeriesSeasonsRoute() {
    composable<SeriesSeasonsRoute>{
        val viewModel: SeriesDetailsScreenScreenViewModel = koinViewModel()
        SeriesSeasonsScreen(viewModel = viewModel)
    }
}