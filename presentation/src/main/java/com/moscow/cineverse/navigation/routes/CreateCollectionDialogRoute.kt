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
            onNavigateBack = { collectionId, collectionName ->
                navController
                    .previousBackStackEntry
                    ?.savedStateHandle
                    ?.apply {
                        set("collection_name", collectionName)
                        set("collection_Id", collectionId)
                    }
                navController.navigateUp()
            },
        )
    }
}