package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.screen.cast_details.best_of_movies.ShowAllActorMoviesScreen
import kotlinx.serialization.Serializable

@Serializable
data class CastBestOfMovieRoute(val castId: Int, val castName: String){
    companion object {
        const val CAST_ID = "castId"
        const val CAST_NAME = "castName"
    }
}

fun NavGraphBuilder.CastBestOfMovieRoute(navController: NavHostController) {
    composable<CastBestOfMovieRoute>{
        ShowAllActorMoviesScreen(
            navigateMovieDetails = { movieId ->
                navController.navigate(MovieDetailsRoute(movieId))
            },
            navigateBack = {
                navController.navigateUp()
            },
        )
    }
}