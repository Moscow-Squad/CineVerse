package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.navigation.AppDestination
import com.moscow.cineverse.screen.see_more.SeeMoreHomeScreen
import kotlinx.serialization.Serializable

@Serializable
data class SeeMoreRoute(val category: String) : AppDestination {
    companion object {
        const val CATEGORY = "category"
    }
}

fun NavGraphBuilder.seeMoreRoute(navController: NavHostController) {
    composable<SeeMoreRoute> {
        SeeMoreHomeScreen(
            navigateToMovieDetails = { movieId ->
                navController.navigate(MovieDetailsRoute(movieId))

            },
            navigateToSeriesDetails = { seriesId ->
                navController.navigate(SeriesDetailsRoute(seriesId))

            },
            navigateToCastDetails = { actorId ->
                navController.navigate(CastDetailsRoute(actorId))

            },
            navigateBack = {
                navController.navigateUp()
            },
        )
    }
}
