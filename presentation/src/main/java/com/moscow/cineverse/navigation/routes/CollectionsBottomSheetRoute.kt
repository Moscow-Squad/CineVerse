package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.moscow.cineverse.screen.collections.CollectionsBottomSheetScreen
import kotlinx.serialization.Serializable

@Serializable
data class CollectionsBottomSheetRoute(
    val mediaItemId: Int,
    val mediaType: String = "movie"
) {
    companion object {
        const val MEDIA_ITEM_ID = "mediaItemId"
        const val MEDIA_TYPE = "mediaType"
    }
}

fun NavGraphBuilder.collectionsBottomSheetRoute(
    onAddNewCollectionClick: () -> Unit,
    onCreateCollectionClicked: () -> Unit,
    navigateBack: () -> Unit
) {
    dialog<CollectionsBottomSheetRoute> {
        CollectionsBottomSheetScreen(
            onAddNewCollectionClick = onAddNewCollectionClick,
            onCreateCollectionClicked = onCreateCollectionClicked,
            navigateBack = navigateBack
        )
    }
}