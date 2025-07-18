package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.moscow.cineverse.screen.castDetails.gallery.ActorGalleryScreen
import kotlinx.serialization.Serializable

@Serializable
data class CastGalleryRoute(val castId: Int, val castName: String)

fun NavGraphBuilder.CastGalleryRoute() {
    composable<CastGalleryRoute>{
        val args = it.toRoute<CastGalleryRoute>()
        ActorGalleryScreen(

            actorId = args.castId,
            title = args.castName,
        )
    }
}