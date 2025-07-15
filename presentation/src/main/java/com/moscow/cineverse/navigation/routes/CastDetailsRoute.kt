package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class CastDetailsRoute(val castId: Int)

fun NavGraphBuilder.CastDetailsRoute(navController: NavController) {
    composable<CastDetailsRoute>{
        val args = it.toRoute<CastDetailsRoute>()
    }
}