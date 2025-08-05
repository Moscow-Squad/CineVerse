package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.screen.movie_details.MovieDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsRoute(val movieId: Int) {
    companion object {
        const val MOVIE_ID = "movieId"
    }
}

fun NavGraphBuilder.MovieDetailsRoute(navController: NavHostController) {
    composable<MovieDetailsRoute>{
        MovieDetailsScreen(
            navigateBack = { navController.navigateUp() },
            navigateToRecommendations = { movieId, movieTitle ->
                navController.navigate(
                    RecommendationsRoute(movieId, movieTitle)
                )
            },
            navigateToReviews = { movieId ->
                navController.navigate(ReviewsRoute(movieId, true))
            },
            navigateToCastDetails = { castId ->
                navController.navigate(CastDetailsRoute(castId))
            },
            navigateToCollectionsBottomSheet = { movieId ->
                navController.navigate(CollectionsBottomSheetRoute(movieId))
            },
            navigateToMovieDetails = { movieId ->
                navController.navigate(MovieDetailsRoute(movieId))
            },
            navigateToLogin = { navController.navigate(LoginRoute) }
        )
    }
}