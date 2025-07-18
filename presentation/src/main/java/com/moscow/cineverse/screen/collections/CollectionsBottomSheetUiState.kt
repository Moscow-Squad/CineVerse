package com.moscow.cineverse.screen.collections

import com.moscow.cineverse.screen.model.CollectionUi


data class CollectionsBottomSheetUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val showBottomSheet: Boolean = false,
    val collections: List<CollectionUi> = emptyList(),
    val createCollection: Boolean = false,
    val showProcessIndicator: Boolean = false
)