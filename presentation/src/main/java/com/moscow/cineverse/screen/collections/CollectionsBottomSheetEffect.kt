package com.moscow.cineverse.screen.collections

sealed interface CollectionsBottomSheetEffect {
    data object OnLoginClicked : CollectionsBottomSheetEffect
    data object OnCreateCollectionClicked : CollectionsBottomSheetEffect
    object OnItemAddedSuccessfully : CollectionsBottomSheetEffect
    data class OnItemAddedFailed(val message: Int) : CollectionsBottomSheetEffect
}