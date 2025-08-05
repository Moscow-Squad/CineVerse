package com.moscow.cineverse.screen.collections

import com.moscow.cineverse.common_ui_state.CollectionUiState


data class CollectionsBottomSheetScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val showBottomSheet: Boolean = false,
    val collections: List<CollectionUiState> = emptyList(),
    val showProcessIndicator: Boolean = false,
    val isLoggedIn: Boolean? = null,
    )