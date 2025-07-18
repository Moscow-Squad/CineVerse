package com.moscow.cineverse.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.android.domain.repository.PreferenceRepository
import com.moscow.cineverse.navigation.routes.CastBestOfMovieRoute
import com.moscow.cineverse.navigation.routes.CastDetailsRoute
import com.moscow.cineverse.navigation.routes.CastGalleryRoute
import com.moscow.cineverse.navigation.routes.ExploreRoute
import com.moscow.cineverse.navigation.routes.LoginRoute
import com.moscow.cineverse.navigation.routes.MovieDetailsRoute
import com.moscow.cineverse.navigation.routes.RecommendationsRoute
import com.moscow.cineverse.navigation.routes.ReviewsRoute
import com.moscow.cineverse.navigation.routes.SeriesDetailsRoute
import com.moscow.cineverse.navigation.routes.exploreRoute
import com.moscow.cineverse.navigation.routes.loginRoute
import org.koin.compose.getKoin

val LocalNavController =
    staticCompositionLocalOf<NavHostController> { error("No NavController provided") }

@Composable

fun CineVerseNavGraph(
    preferenceRepository: PreferenceRepository = getKoin().get()
) {
    val navController = rememberNavController()

    var startDestination by remember { mutableStateOf<Any>(LoginRoute) }

    LaunchedEffect(Unit) {
        if (preferenceRepository.areLoggedIn()) {
            startDestination = ExploreRoute
        }
    }

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(navController = navController, startDestination = startDestination) {
            exploreRoute()
            CastDetailsRoute(navController)
            CastGalleryRoute(navController)
            CastBestOfMovieRoute(navController)
            MovieDetailsRoute(navController)
            SeriesDetailsRoute(navController)
            loginRoute(navController)
        }
    }
}