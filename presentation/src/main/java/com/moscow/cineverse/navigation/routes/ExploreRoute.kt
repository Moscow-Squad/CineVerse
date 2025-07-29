package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.navigation.AppDestination
import com.moscow.cineverse.screen.explore.ExploreScreen
import kotlinx.serialization.Serializable

@Serializable
object ExploreRoute : AppDestination

fun NavGraphBuilder.exploreRoute(navController: NavHostController) {
    composable<ExploreRoute>{
        ExploreScreen(
            navigateToCastDetails = { actorId ->
                navController.navigate(CastDetailsRoute(actorId))
            },
            navigateToMovieDetails = { movieId ->
                navController.navigate(MovieDetailsRoute(movieId))
            },
            navigateToSeriesDetails = { seriesId ->
                navController.navigate(SeriesDetailsRoute(seriesId))
            }
        )
    }
}