package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.navigation.AppDestination
import com.moscow.cineverse.navigation.navigateToNewGraph
import com.moscow.cineverse.screen.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute : AppDestination

fun NavGraphBuilder.homeRoute(navController: NavHostController) {
    composable<HomeRoute> {
        HomeScreen(
            navigateToMovieDetails = { movieId ->
                navController.navigate(
                    MovieDetailsRoute(movieId)
                )
            },
            navigateToSeeMoreHome = { category ->
                navController.navigate(SeeMoreRoute(category))

            },
            navigateToSeriesDetails = { seriesId ->
                navController.navigate(
                    SeriesDetailsRoute(seriesId)
                )
            },
            navigateToBrowseSuggestion = {
                navController.navigateToNewGraph(ExploreRoute)
            },
            navigateToWatchingSuggestion = {
                navController.navigateToNewGraph(MatchRoute)
            },
            navigateToCollectionDetails = { id, name ->
                navController.navigate(CollectionDetailsRoute(id, name))
            },
        )
    }
}