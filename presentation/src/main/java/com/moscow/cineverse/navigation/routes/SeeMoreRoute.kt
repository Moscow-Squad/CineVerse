package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.moscow.cineverse.navigation.AppDestination
import com.moscow.cineverse.screen.see_more.SeeMoreHomeScreen
import kotlinx.serialization.Serializable

@Serializable
data class SeeMoreRoute(val category: String) : AppDestination {
    companion object {
        const val route = "see_more/{category}"
        fun createRoute(category: String) = "see_more/$category"
    }
}

fun NavGraphBuilder.seeMoreRoute() {
    composable(
        route = SeeMoreRoute.route,
        arguments = listOf(
            navArgument("category") { type = NavType.StringType }
        )
    ) {
        val category = it.arguments?.getString("category") ?: ""
        SeeMoreHomeScreen()
    }
}
