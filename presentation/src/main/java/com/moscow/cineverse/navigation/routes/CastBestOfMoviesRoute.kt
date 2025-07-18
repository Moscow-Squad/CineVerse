package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.moscow.cineverse.screen.castDetails.best0fmovies.ShowAllActorMoviesScreen
import kotlinx.serialization.Serializable

@Serializable
data class CastBestOfMovieRoute(val castId: Int, val castName: String)

fun NavGraphBuilder.CastBestOfMovieRoute() {
    composable<CastBestOfMovieRoute>{
        val args = it.toRoute<CastBestOfMovieRoute>()
        ShowAllActorMoviesScreen(

            actorId = args.castId,
            title = args.castName,
            onNavigateBack = {navController.popBackStack()}
        )
    }
}