package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.moscow.cineverse.navigation.AppDestination
import com.moscow.cineverse.screen.login.WebViewBrowser
import com.moscow.cineverse.screen.movie_details.MovieDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class WebViewRoute(val url: String): AppDestination

fun NavGraphBuilder.webViewRoute(navController: NavHostController) {
    composable<WebViewRoute>{
        val args = it.toRoute<WebViewRoute>()
        WebViewBrowser(url = args.url) {
            navController.navigateUp()
        }
    }
}