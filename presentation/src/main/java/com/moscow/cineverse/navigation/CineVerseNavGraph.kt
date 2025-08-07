package com.moscow.cineverse.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.moscow.cineverse.navigation.routes.HistoryRoute
import com.moscow.cineverse.navigation.routes.castBestOfMovieRoute
import com.moscow.cineverse.navigation.routes.castDetailsRoute
import com.moscow.cineverse.navigation.routes.castGalleryRoute
import com.moscow.cineverse.navigation.routes.collectionDetailsRoute
import com.moscow.cineverse.navigation.routes.movieDetailsRoute
import com.moscow.cineverse.navigation.routes.recommendationsRoute
import com.moscow.cineverse.navigation.routes.reviewsRoute
import com.moscow.cineverse.navigation.routes.seriesDetailsRoute
import com.moscow.cineverse.navigation.routes.seriesRecommendationRoute
import com.moscow.cineverse.navigation.routes.seriesSeasonsRoute
import com.moscow.cineverse.navigation.routes.collectionsBottomSheetRoute
import com.moscow.cineverse.navigation.routes.exploreRoute
import com.moscow.cineverse.navigation.routes.historyRoute
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
            recommendationsRoute(navController)
            reviewsRoute(navController)
            castDetailsRoute(navController)
            castGalleryRoute(navController)
            castBestOfMovieRoute(navController)
            movieDetailsRoute(navController)
            seriesDetailsRoute(navController)
            seriesRecommendationRoute(navController)
            seriesSeasonsRoute(navController)
            collectionsBottomSheetRoute(navController)
            homeRoute(navController)
            seeMoreRoute(navController)
            matchRoute(navController)
            profileRoute(navController)
            collectionDetailsRoute(navController)
            historyRoute(navController)
        }
    }
}