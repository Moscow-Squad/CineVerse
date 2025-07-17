package com.moscow.cineverse.screen.collections

data class CollectionsUiState(
    val isLoading: Boolean = false,
    val collections: List<CollectionUi>
) {
    data class CollectionUi(
        val id: Int,
        val name: String,
    )
}
