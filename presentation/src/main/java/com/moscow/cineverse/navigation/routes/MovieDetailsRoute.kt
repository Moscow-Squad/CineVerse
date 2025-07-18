package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.moscow.cineverse.screen.castDetails.CastDetailsScreen
import com.moscow.cineverse.screen.movie_details.MovieDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsRoute(val movieId: Int)

fun NavGraphBuilder.MovieDetailsRoute() {
    composable<MovieDetailsRoute>{
        val args = it.toRoute<MovieDetailsRoute>()
        MovieDetailsScreen(

            movieId = args.movieId,
        )
    }
}