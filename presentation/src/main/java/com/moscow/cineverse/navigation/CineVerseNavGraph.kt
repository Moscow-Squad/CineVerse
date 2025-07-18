package com.moscow.cineverse.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.android.domain.repository.PreferenceRepository
import com.moscow.cineverse.navigation.routes.CastBestOfMovieRoute
import com.moscow.cineverse.navigation.routes.CastDetailsRoute
import com.moscow.cineverse.navigation.routes.CastGalleryRoute
import com.moscow.cineverse.navigation.routes.ExploreRoute
import com.moscow.cineverse.navigation.routes.LoginRoute
import com.moscow.cineverse.navigation.routes.MovieDetailsRoute
import com.moscow.cineverse.navigation.routes.SeriesDetailsRoute
import com.moscow.cineverse.navigation.routes.loginRoute
import org.koin.compose.getKoin

@Composable
fun CineVerseNavGraph(
    navController: NavHostController,
    preferenceRepository: PreferenceRepository = getKoin().get()
) {
    var startDestination by remember { mutableStateOf<Any>(LoginRoute) }

    LaunchedEffect(Unit) {
        if (preferenceRepository.areLoggedIn()) {
            startDestination = ExploreRoute
        }
    }

    NavHost(navController = navController, startDestination = startDestination) {
        ExploreRoute(navController)
        CastDetailsRoute(navController)
        CastGalleryRoute(navController)
        CastBestOfMovieRoute(navController)
        MovieDetailsRoute(navController)
        SeriesDetailsRoute(navController)
        loginRoute(navController)
    }
}