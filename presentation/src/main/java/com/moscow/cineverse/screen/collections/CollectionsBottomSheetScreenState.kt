package com.moscow.cineverse.screen.collections

import com.moscow.domain.model.Collection

data class CollectionsBottomSheetScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val showBottomSheet: Boolean = false,
    val collections: List<CollectionUiState> = emptyList(),
    val showProcessIndicator: Boolean = false,
    val isLoggedIn: Boolean? = null,

    )

data class CollectionUiState(
    val id: Int,
    val name: String,
    val isLoading: Boolean = false
)

fun Collection.toUi() =
    CollectionUiState(
        id = id,
        name = name,
        isLoading = false
    )