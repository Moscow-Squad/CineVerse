package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.moscow.cineverse.screen.movie_details.recommendations.RecommendationMoviesScreen
import kotlinx.serialization.Serializable

@Serializable
data class RecommendationsRoute(val id: Int , val movieTitle:String)

fun NavGraphBuilder.RecommendationsRoute() {
    composable<RecommendationsRoute> {
        val args = it.toRoute<RecommendationsRoute>()
        RecommendationMoviesScreen(
            movieId = args.id,
            title = args.movieTitle
        )
    }
}