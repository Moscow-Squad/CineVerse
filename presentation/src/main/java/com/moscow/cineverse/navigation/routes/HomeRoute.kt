package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moscow.cineverse.navigation.AppDestination
import com.moscow.cineverse.screen.login.LoginScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute : AppDestination

fun NavGraphBuilder.homeRoute() {
    composable<HomeRoute>{
    }
}