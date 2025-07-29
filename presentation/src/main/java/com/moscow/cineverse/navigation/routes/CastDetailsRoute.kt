package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moscow.cineverse.screen.cast_details.CastDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class CastDetailsRoute(val castId: Int){
    companion object{
        const val CAST_ID = "castId"

    }
}

fun NavGraphBuilder.CastDetailsRoute() {
    composable<CastDetailsRoute>{
        CastDetailsScreen()
    }
}