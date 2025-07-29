package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.navigation.AppDestination
import kotlinx.serialization.Serializable

@Serializable
object ProfileRoute : AppDestination

fun NavGraphBuilder.profileRoute(navController: NavHostController) {
    composable<ProfileRoute>{
    }
}