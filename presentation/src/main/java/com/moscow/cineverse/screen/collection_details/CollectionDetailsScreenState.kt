package com.moscow.cineverse.screen.collection_details

import com.moscow.cineverse.screen.explore.ExploreScreenState.GenreUiState

data class CollectionDetailsScreenState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMsg: String = "",
    val showTip: Boolean = true,
    val moviesGenres: List<GenreUiState> = emptyList(),
    val seriesGenres: List<GenreUiState> = emptyList(),
)
