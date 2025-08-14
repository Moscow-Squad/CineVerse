package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.screen.actor_best_of_movies.ActorBestMoviesScreen
import kotlinx.serialization.Serializable

@Serializable
data class CastBestOfMovieRoute(val castId: Int, val castName: String){
    companion object {
        const val CAST_ID = "castId"
        const val CAST_NAME = "castName"
    }
}

fun NavGraphBuilder.castBestOfMovieRoute(navController: NavHostController) {
    composable<CastBestOfMovieRoute>{
        ActorBestMoviesScreen(
            navigateMovieDetails = { movieId ->
                navController.navigate(MovieDetailsRoute(movieId))
            },
            navigateBack = {
                navController.navigateUp()
            },
        )
    }
}