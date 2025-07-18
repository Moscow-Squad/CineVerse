package com.moscow.cineverse.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moscow.cineverse.screen.collections.CollectionsBottomSheetScreen
import kotlinx.serialization.Serializable

@Serializable
data object CollectionsBottomSheetRoute

fun NavGraphBuilder.collectionsBottomSheetRoute(
    onAddNewCollectionClick: () -> Unit,
    onCreateCollectionClicked: () -> Unit,
    navigateBack: () -> Unit
) {
    composable<CollectionsBottomSheetRoute> {
        CollectionsBottomSheetScreen(
            onAddNewCollectionClick = onAddNewCollectionClick,
            onCreateCollectionClicked = onCreateCollectionClicked,
            navigateBack = navigateBack
        )
    }
}