package com.moscow.cineverse.screen.collections

sealed interface CollectionsBottomSheetEffect {
    data object OnLoginClicked : CollectionsBottomSheetEffect
    data object OnCreateCollectionClicked : CollectionsBottomSheetEffect
    data class OnMovieAddedSuccessfully(val message: String) : CollectionsBottomSheetEffect
}