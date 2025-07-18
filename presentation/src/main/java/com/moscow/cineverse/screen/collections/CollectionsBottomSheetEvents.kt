package com.moscow.cineverse.screen.collections

sealed interface CollectionsBottomSheetEvents {
    data object OnLoginClicked : CollectionsBottomSheetEvents
    data object OnCreateCollectionClicked : CollectionsBottomSheetEvents
}