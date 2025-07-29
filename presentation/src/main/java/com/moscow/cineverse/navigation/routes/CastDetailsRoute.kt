package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.moscow.cineverse.screen.castDetails.CastDetailsScreen
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