package com.moscow.cineverse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.moscow.cineverse.navigation.routes.CastBestOfMovieRoute
import com.moscow.cineverse.navigation.routes.CastDetailsRoute
import com.moscow.cineverse.navigation.routes.CastGalleryRoute
import com.moscow.cineverse.navigation.routes.ExploreRoute
import com.moscow.cineverse.navigation.routes.MovieDetailsRoute
import com.moscow.cineverse.navigation.routes.SeriesDetailsRoute

@Composable
fun CineVerseNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = CastDetailsRoute(castId = 679)) {
        ExploreRoute(navController)
        CastDetailsRoute(navController)
        CastGalleryRoute(navController)
        CastBestOfMovieRoute(navController)
        MovieDetailsRoute(navController)
        SeriesDetailsRoute(navController)
    }
}