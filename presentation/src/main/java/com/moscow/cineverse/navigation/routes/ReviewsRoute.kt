package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.moscow.cineverse.screen.reviews.ReviewsScreen
import kotlinx.serialization.Serializable

@Serializable
data class ReviewsRoute(val id: Int, val isMovie: Boolean)

fun NavGraphBuilder.ReviewsRoute() {
    composable<ReviewsRoute>{
        val args = it.toRoute<ReviewsRoute>()
        ReviewsScreen(
            movieId = args.id,
            isMovie = args.isMovie,
        )
    }
}