package com.moscow.cineverse.screen.my_collections

data class MyCollectionsUiState(
    val errorMessage: Int? = null,
    val isLoading: Boolean = false,
    val collections: List<MyCollectionUiState> = emptyList(),
)
