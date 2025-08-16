package com.moscow.cineverse.mapper

import com.moscow.cineverse.common_ui_state.CollectionUiState
import com.moscow.domain.model.Collection

fun Collection.toCollectionUi() =
    CollectionUiState(
        id = id,
        title = name,
        numberOfItems = itemCount,
    )