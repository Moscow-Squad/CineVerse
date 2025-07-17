package com.moscow.cineverse.screen.collections

import com.android.domain.model.Collection

data class CollectionsBottomSheetUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val showBottomSheet: Boolean = false,
    val collections: List<Collection> = emptyList(),
    val showProcessIndicator: Boolean = false
) {
    data class CollectionUi(
        val id: Int,
        val name: String,
        val isLoading: Boolean = false
    )
}

