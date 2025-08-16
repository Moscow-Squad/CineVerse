package com.moscow.cineverse.screen.my_collections

import com.moscow.cineverse.common_ui_state.CollectionUiState

data class MyCollectionsUiState(
    val errorMessage: Int? = null,
    val isLoading: Boolean = false,
    val collections: List<CollectionUiState> = emptyList(),
)
