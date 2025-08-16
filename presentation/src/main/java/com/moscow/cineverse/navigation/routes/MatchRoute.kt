package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.navigation.AppDestination
import com.moscow.cineverse.screen.match.MatchScreen
import kotlinx.serialization.Serializable

@Serializable
object MatchRoute : AppDestination

fun NavGraphBuilder.matchRoute(navController: NavHostController, onBottomNavVisibilityChange: (Boolean) -> Unit) {
    composable<MatchRoute> { entry ->
        MatchScreen(
            navigateToMovieDetails = { movieId -> navController.navigate(MovieDetailsRoute(movieId)) },
            navigateToCollectionsBottomSheet = { id ->
                navController.navigate(
                    CollectionsBottomSheetRoute(
                        id
                    )
                )
            },
            onBottomNavVisibilityChange = onBottomNavVisibilityChange,
        )
    }
}