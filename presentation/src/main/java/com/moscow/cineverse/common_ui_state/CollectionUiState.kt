package com.moscow.cineverse.common_ui_state

data class CollectionUiState(
    val id: Int,
    val title: String,
    val numberOfItems: Int,
    val isLoading: Boolean = false
)
