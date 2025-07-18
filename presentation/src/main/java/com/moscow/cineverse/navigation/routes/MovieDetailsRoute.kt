package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moscow.cineverse.screen.movie_details.MovieDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsRoute(val movieId: Int) {
    companion object {
        const val MOVIE_ID = "movieId"
    }
}

fun NavGraphBuilder.MovieDetailsRoute() {
    composable<MovieDetailsRoute>{
        MovieDetailsScreen()
    }
}