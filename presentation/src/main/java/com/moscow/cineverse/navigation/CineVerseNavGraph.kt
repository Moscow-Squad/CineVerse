package com.moscow.cineverse.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.moscow.cineverse.navigation.routes.CastBestOfMovieRoute
import com.moscow.cineverse.navigation.routes.CastDetailsRoute
import com.moscow.cineverse.navigation.routes.CastGalleryRoute
import com.moscow.cineverse.navigation.routes.ExploreRoute
import com.moscow.cineverse.navigation.routes.MovieDetailsRoute
import com.moscow.cineverse.navigation.routes.RecommendationsRoute
import com.moscow.cineverse.navigation.routes.ReviewsRoute
import com.moscow.cineverse.navigation.routes.SeriesDetailsRoute
import com.moscow.cineverse.navigation.routes.SeriesRecommendationRoute
import com.moscow.cineverse.navigation.routes.collectionsBottomSheetRoute
import com.moscow.cineverse.navigation.routes.exploreRoute

val LocalNavController =
    staticCompositionLocalOf<NavHostController> { error("No NavController provided") }

@Composable

fun CineVerseNavGraph() {
    val navController = rememberNavController()
    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(navController = navController, startDestination = ExploreRoute) {
            exploreRoute()
            RecommendationsRoute()
            ReviewsRoute()
            CastDetailsRoute()
            CastGalleryRoute()
            CastBestOfMovieRoute()
            MovieDetailsRoute()
            SeriesDetailsRoute()
            SeriesRecommendationRoute()
            collectionsBottomSheetRoute(
                onAddNewCollectionClick = {},
                onCreateCollectionClicked = {},
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}