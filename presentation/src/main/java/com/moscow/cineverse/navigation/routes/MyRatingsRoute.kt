package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.navigation.AppDestination
import com.moscow.cineverse.navigation.navigateToNewGraph
import com.moscow.cineverse.screen.myratings.MyRatingScreen
import kotlinx.serialization.Serializable

@Serializable
object MyRatingsRoute : AppDestination

fun NavGraphBuilder.myRatingsRoute(navController: NavHostController) {
    composable<MyRatingsRoute> {
        MyRatingScreen(
            navigateBack = {
                navController.navigateUp()
            },
            navigateToMovieDetails = { movieId ->
                navController.navigate(
                    MovieDetailsRoute(movieId)
                )
            },
            navigateToSeriesDetails = { seriesId ->
                navController.navigate(
                    SeriesDetailsRoute(seriesId)
                )
            },
            navigateToExplore = {
                navController.navigateToNewGraph(ExploreRoute)
            }
        )
    }
}