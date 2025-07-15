package com.moscow.cineverse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.moscow.cineverse.navigation.routes.ExploreRoute

@Composable
fun CineVerseNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = ExploreRoute) {
        ExploreRoute(navController)
    }
}