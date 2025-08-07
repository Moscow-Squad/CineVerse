package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.navigation.AppDestination
import com.moscow.cineverse.screen.splash.SplashScreen
import kotlinx.serialization.Serializable

@Serializable
object SplashRoute: AppDestination

fun NavGraphBuilder.splashRoute(navController: NavHostController){
    composable<SplashRoute> {
        SplashScreen(
            navigateToHome = {
                navController.navigate(HomeRoute){
                    popUpTo(SplashRoute) { inclusive = true }
                }
            },
            navigateToLogin = {
                navController.navigate(LoginRoute) {
                    popUpTo(SplashRoute) { inclusive = true }
                }
            } ,
            navigateToOnboarding = {
                navController.navigate(OnBoardingRoute) {
                    popUpTo(SplashRoute) { inclusive = true }
                }
            }
        )
    }
}