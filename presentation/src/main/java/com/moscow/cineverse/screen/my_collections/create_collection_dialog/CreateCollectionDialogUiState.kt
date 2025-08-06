package com.moscow.cineverse.screen.my_collections.create_collection_dialog

data class CreateCollectionDialogUiState(
    val collectionName: String = "",
    val collectionId: Int? = null,
    val isLoading: Boolean = false,
)