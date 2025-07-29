package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moscow.cineverse.screen.castDetails.gallery.ActorGalleryScreen
import kotlinx.serialization.Serializable

@Serializable
data class CastGalleryRoute(val castId: Int, val castName: String){
    companion object {
        const val CAST_ID = "castId"
        const val CAST_NAME = "castName"
    }
}


fun NavGraphBuilder.CastGalleryRoute() {
    composable<CastGalleryRoute>{
        ActorGalleryScreen()
    }
}