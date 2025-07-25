package com.moscow.cineverse.screen.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moscow.cineverse.navigation.AppDestination
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute : AppDestination

fun NavGraphBuilder.homeRoute() {
    composable<HomeRoute> {
        HomeScreen()
    }
}