package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.moscow.cineverse.screen.my_collections.create_collection_dialog.CreateCollectionDialog
import kotlinx.serialization.Serializable

@Serializable
data object CreateCollectionDialogRoute

fun NavGraphBuilder.createCollectionDialogRoute(navController: NavController) {
    dialog<CreateCollectionDialogRoute> {
        CreateCollectionDialog(
            onNavigateBack = navController::navigateUp,
        )
    }
}