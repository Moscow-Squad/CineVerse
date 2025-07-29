package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.screen.series_details.SeriesDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class SeriesDetailsRoute(val seriesId: Int) {
    companion object {
        const val SERIES_ID = "seriesId"
    }
}

fun NavGraphBuilder.SeriesDetailsRoute(navController: NavHostController) {
    composable<SeriesDetailsRoute>{
        SeriesDetailsScreen(
            navigateBack = {
                navController.navigateUp()
            },
            navigateToCollectionBottomSheet = { seriesId ->
                navController.navigate(CollectionsBottomSheetRoute(mediaItemId = seriesId))

            },
            navigateToSeriesRecommendation = { seriesId ->
                navController.navigate(SeriesRecommendationRoute(seriesId))
            },
            navigateToReviews = { seriesId ->
                navController.navigate(ReviewsRoute(seriesId, false))
            },
            navigateToSeriesSeasons = { seriesId ->
                navController.navigate(SeriesSeasonsRoute(seriesId))
            },
            navigateToCastDetails = { castId ->
                navController.navigate(CastDetailsRoute(castId))
            },
            navigateToSeriesDetails = { seriesId ->
                navController.navigate(SeriesDetailsRoute(seriesId))
            },

            )
    }
}