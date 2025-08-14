package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.details.movie_details.recommendations.RecommendationMoviesScreen
import kotlinx.serialization.Serializable

@Serializable
data class RecommendationsRoute(val movieId: Int, val movieTitle: String) {
    companion object {
        const val MOVIE_ID = "movieId"
        const val MOVIE_TITLE = "movieTitle"
    }
}

fun NavGraphBuilder.recommendationsRoute(navController: NavHostController) {
    composable<RecommendationsRoute> {
        RecommendationMoviesScreen(
            navigateBack = { navController.navigateUp() },
            navigateToMovieDetails = { movieId ->
                navController.navigate(MovieDetailsRoute(movieId))
            },
        )
    }
}