package com.moscow.cineverse.screen

import com.android.domain.model.Collection

data class CollectionsBottomSheetUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val showBottomSheet: Boolean = false,
    val collections: List<Collection> = emptyList(),
    val showProcessIndicator: Boolean = false
)