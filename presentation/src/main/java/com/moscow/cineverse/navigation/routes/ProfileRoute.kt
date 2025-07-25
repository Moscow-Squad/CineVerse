package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moscow.cineverse.navigation.AppDestination
import kotlinx.serialization.Serializable

@Serializable
object ProfileRoute : AppDestination

fun NavGraphBuilder.profileRoute() {
    composable<ProfileRoute>{
    }
}