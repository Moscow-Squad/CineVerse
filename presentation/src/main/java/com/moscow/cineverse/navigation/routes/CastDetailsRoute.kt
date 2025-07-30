package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.screen.cast_details.CastDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class CastDetailsRoute(val castId: Int){
    companion object{
        const val CAST_ID = "castId"

    }
}

fun NavGraphBuilder.castDetailsRoute(navController: NavHostController) {
    composable<CastDetailsRoute>{
        CastDetailsScreen(
            navigateBack = { navController.navigateUp() },
            navigateToMovieDetails = { movieId ->
                navController.navigate(MovieDetailsRoute(movieId))
            },
            navigateToCastBestOfMovie = { actorId, actorName ->
                navController.navigate(
                    CastBestOfMovieRoute(actorId, actorName)
                )
            },
            navigateToCastGallery = { actorId, actorName ->
                navController.navigate(
                    CastGalleryRoute(actorId, actorName)
                )
            }
        )
    }
}