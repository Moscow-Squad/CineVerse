package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.navigation.AppDestination
import com.moscow.cineverse.screen.login.LoginScreen
import com.moscow.cineverse.screen.login.WebViewBrowser
import com.moscow.cineverse.screen.profile.ProfileScreen
import kotlinx.serialization.Serializable

@Serializable
object ProfileRoute : AppDestination

fun NavGraphBuilder.profileRoute(navController: NavHostController) {
    composable<ProfileRoute>{
        ProfileScreen(
            navigateToWebSite = { url->
              //  navController.navigate(WebViewBrowser(url) { })
            },
            navigateToLogin = {
                navController.navigate(LoginRoute)
            },
            navigateToMyRatings = {
                navController.navigate(MyRatingsRoute)

            },
            navigateToMyCollections = {
                navController.navigate(MyCollectionsRoute)
            },
            navigateToMyHistory = {
                navController.navigate(HistoryRoute)

            }
        )
    }
}