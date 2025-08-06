package com.moscow.cineverse.mapper

import com.moscow.cineverse.common_ui_state.MyCollectionUiState
import com.moscow.cineverse.screen.collections.CollectionUiState
import com.moscow.domain.model.Collection


fun Collection.toCollectionUi() =
    CollectionUiState(
        id = id,
        name = name,
        isLoading = false
    )


fun Collection.toMyCollectionUi() =
    MyCollectionUiState(
        id = id,
        title = name,
        numberOfShows = itemCount
    )