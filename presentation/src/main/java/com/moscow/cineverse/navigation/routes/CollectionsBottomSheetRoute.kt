package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.dialog
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.collections.CollectionsBottomSheetScreen
import kotlinx.serialization.Serializable

@Serializable
data class CollectionsBottomSheetRoute(
    val mediaItemId: Int,
    val mediaType: String = MediaItemUiState.MediaType.Movie.name
) {
    companion object {
        const val MEDIA_ITEM_ID = "mediaItemId"
        const val MEDIA_TYPE = "mediaType"
    }
}

fun NavGraphBuilder.collectionsBottomSheetRoute(
    navController: NavHostController
) {
    dialog<CollectionsBottomSheetRoute> {
        CollectionsBottomSheetScreen(
            onCreateCollectionClicked = { navController.navigate(MyCollectionsRoute) },
            navigateBack = navController::navigateUp,
            onLogIn = { navController.navigate(LoginRoute) }
        )
    }
}