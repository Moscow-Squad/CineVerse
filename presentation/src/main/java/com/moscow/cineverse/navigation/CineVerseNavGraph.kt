package com.moscow.cineverse.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.moscow.cineverse.navigation.routes.CastBestOfMovieRoute
import com.moscow.cineverse.navigation.routes.CastDetailsRoute
import com.moscow.cineverse.navigation.routes.CastGalleryRoute
import com.moscow.cineverse.navigation.routes.MovieDetailsRoute
import com.moscow.cineverse.navigation.routes.RecommendationsRoute
import com.moscow.cineverse.navigation.routes.ReviewsRoute
import com.moscow.cineverse.navigation.routes.SeriesDetailsRoute
import com.moscow.cineverse.navigation.routes.SeriesRecommendationRoute
import com.moscow.cineverse.navigation.routes.SeriesSeasonsRoute
import com.moscow.cineverse.navigation.routes.collectionsBottomSheetRoute
import com.moscow.cineverse.navigation.routes.exploreRoute
import com.moscow.cineverse.navigation.routes.homeRoute
import com.moscow.cineverse.screen.home.HomeRoute
import com.moscow.cineverse.screen.home.homeScreen
import com.moscow.cineverse.navigation.routes.loginRoute
import com.moscow.cineverse.navigation.routes.matchRoute
import com.moscow.cineverse.navigation.routes.profileRoute
import org.koin.androidx.compose.koinViewModel


val LocalNavController =
    staticCompositionLocalOf<NavHostController> { error("No NavController provided") }

@Composable
fun CineVerseNavGraph(
    navViewModel: NavViewModel = koinViewModel(),
    navController: NavHostController
) {
    val startDestination = navViewModel.startDestination.value

    if (startDestination == null) return

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(navController = navController, startDestination = startDestination) {
            exploreRoute()
            loginRoute()
            RecommendationsRoute()
            ReviewsRoute()
            CastDetailsRoute()
            CastGalleryRoute()
            CastBestOfMovieRoute()
            MovieDetailsRoute()
            SeriesDetailsRoute()
            SeriesRecommendationRoute()
            SeriesSeasonsRoute()
            collectionsBottomSheetRoute(
                onAddNewCollectionClick = {},
                onCreateCollectionClicked = {},
                navigateBack = navController::navigateUp
            )
            homeScreen()
            homeRoute()
            matchRoute()
            profileRoute()
        }
    }
}
