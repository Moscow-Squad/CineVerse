package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.screen.collection_details.CollectionDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class CollectionDetailsRoute(val collectionId: Int, val collectionName: String) {
    companion object {
        const val COLLECTION_ID = "collectionId"
        const val COLLECTION_NAME = "collectionName"
    }
}

fun NavGraphBuilder.collectionDetailsRoute(navController: NavHostController) {
    composable<CollectionDetailsRoute>{
        CollectionDetailsScreen(
            navigateBack = { navController.navigateUp() },
            navigateToMovieDetails = { movieId ->
                navController.navigate(MovieDetailsRoute(movieId))
            },
            navigateToSeriesDetails = { seriesId ->
                navController.navigate(SeriesDetailsRoute(seriesId))
            },
            { navController.navigate(ExploreRoute) }
        )
    }
}