package com.moscow.cineverse.screen.my_collections

import com.moscow.cineverse.common_ui_state.MyCollectionUiState

data class MyCollectionsUiState(
    val errorMessage: Int? = null,
    val isLoading: Boolean = false,
    val collections: List<MyCollectionUiState> = emptyList(),
)
