package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.navigation.AppDestination
import com.moscow.cineverse.screen.profile.ProfileScreen
import kotlinx.serialization.Serializable

@Serializable
object ProfileRoute : AppDestination

fun NavGraphBuilder.profileRoute(navController: NavHostController) {
    composable<ProfileRoute>{
        ProfileScreen(
            navigateToEditProfile = {accountId, sessionId -> },
            navigateToHome = { -> },
            navigateToMyRatings = {},
            navigateToMyCollections = {},
            navigateToMyHistory = {}
        )
    }
}