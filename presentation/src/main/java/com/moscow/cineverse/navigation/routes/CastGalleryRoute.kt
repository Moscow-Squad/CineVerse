package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moscow.cineverse.screen.cast_details.gallery.ActorGalleryScreen
import kotlinx.serialization.Serializable

@Serializable
data class CastGalleryRoute(val castId: Int, val castName: String){
    companion object {
        const val CAST_ID = "castId"
        const val CAST_NAME = "castName"
    }
}


fun NavGraphBuilder.castGalleryRoute(navController: NavHostController) {
    composable<CastGalleryRoute>{
        ActorGalleryScreen(
            navigateBack = { navController.navigateUp() },
        )
    }
}