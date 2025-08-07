package com.moscow.cineverse.screen.collections

sealed interface CollectionsBottomSheetEffect {
    data object OnLoginClicked : CollectionsBottomSheetEffect
    data object OnCreateCollectionClicked : CollectionsBottomSheetEffect
    data class OnItemAddedSuccessfully(val message: String) : CollectionsBottomSheetEffect
    data class OnItemAddedFailed(val message: String) : CollectionsBottomSheetEffect
}