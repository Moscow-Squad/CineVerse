package com.moscow.cineverse.common_ui_state

data class CollectionUiState(
    val id: Int,
    val title: String,
    val numberOfShows: Int,
    val isLoading: Boolean = false
)
