package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.screen.history.HistoryScreen
import kotlinx.serialization.Serializable

@Serializable
object HistoryRoute

fun NavGraphBuilder.historyRoute(navController: NavHostController) {
    composable<HistoryRoute> {
        HistoryScreen(
            navigateBack = { navController.popBackStack() },
            navigateToMovieDetails = { movieId -> navController.navigate(MovieDetailsRoute(movieId)) },
            navigateToSeriesDetails = { navController.navigate(SeriesDetailsRoute(it)) }
        )
    }
}