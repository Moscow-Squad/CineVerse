package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.screen.reviews.ReviewsScreen
import kotlinx.serialization.Serializable

@Serializable
data class ReviewsRoute(val id: Int, val isMovie: Boolean) {
    companion object {
        const val ID = "id"
        const val IS_MOVIE = "isMovie"
    }
}

fun NavGraphBuilder.reviewsRoute(navController: NavHostController) {
    composable<ReviewsRoute>{
        ReviewsScreen(
            navigateBack = { navController.navigateUp() },
        )
    }
}