package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.navigation.AppDestination
import com.moscow.cineverse.screen.on_boarding.OnBoardingScreen
import kotlinx.serialization.Serializable

@Serializable
object OnBoardingRoute: AppDestination

fun NavGraphBuilder.onBoardingRoute(navController: NavHostController){
    composable<OnBoardingRoute> {
        OnBoardingScreen(
            navigateToLogin = {
                navController.navigate(LoginRoute) {
                    popUpTo(OnBoardingRoute) { inclusive = true }
                }
            }
        )
    }
}