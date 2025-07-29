package com.moscow.cineverse.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.moscow.cineverse.navigation.routes.CastBestOfMovieRoute
import com.moscow.cineverse.navigation.routes.CastDetailsRoute
import com.moscow.cineverse.navigation.routes.CastGalleryRoute
import com.moscow.cineverse.navigation.routes.HomeRoute
import com.moscow.cineverse.navigation.routes.LoginRoute
import com.moscow.cineverse.navigation.routes.MovieDetailsRoute
import com.moscow.cineverse.navigation.routes.RecommendationsRoute
import com.moscow.cineverse.navigation.routes.ReviewsRoute
import com.moscow.cineverse.navigation.routes.SeriesDetailsRoute
import com.moscow.cineverse.navigation.routes.SeriesRecommendationRoute
import com.moscow.cineverse.navigation.routes.SeriesSeasonsRoute
import com.moscow.cineverse.navigation.routes.collectionsBottomSheetRoute
import com.moscow.cineverse.navigation.routes.exploreRoute
import com.moscow.cineverse.navigation.routes.homeRoute
import com.moscow.cineverse.navigation.routes.loginRoute
import com.moscow.cineverse.navigation.routes.matchRoute
import com.moscow.cineverse.navigation.routes.profileRoute
import com.moscow.cineverse.navigation.routes.seeMoreRoute

val LocalScaffoldPaddingValues =
    staticCompositionLocalOf<PaddingValues> { error("No ScaffoldPadding provided") }

@Composable
fun CineVerseNavGraph(
    modifier: Modifier,
    navViewModel: NavViewModel,
    navController: NavHostController,
    scaffoldPaddingValues: PaddingValues,
) {
    val startDestination = navViewModel.startDestination.value ?: return

    CompositionLocalProvider(
        LocalScaffoldPaddingValues provides scaffoldPaddingValues
    ) {
        NavHost(
            modifier = modifier,
            navController = navController,

            startDestination = startDestination
        ) {

            exploreRoute(navController)
            loginRoute(navController)
            RecommendationsRoute(navController)
            ReviewsRoute(navController)
            CastDetailsRoute(navController)
            CastGalleryRoute(navController)
            CastBestOfMovieRoute(navController)
            MovieDetailsRoute(navController)
            SeriesDetailsRoute(navController)
            SeriesRecommendationRoute(navController)
            SeriesSeasonsRoute(navController)
            collectionsBottomSheetRoute(navController)
            homeRoute(navController)
            seeMoreRoute(navController)
            matchRoute(navController)
            profileRoute(navController)

        }
    }
}