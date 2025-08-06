package com.moscow.cineverse.screen.my_collections

import com.moscow.cineverse.common_ui_state.MyCollectionUiState

data class MyCollectionsUiState(
    val error: String? = null,
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val collections: List<MyCollectionUiState> = emptyList(),

)
