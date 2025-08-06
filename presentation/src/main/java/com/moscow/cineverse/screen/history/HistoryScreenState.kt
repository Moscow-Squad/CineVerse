package com.moscow.cineverse.screen.history

import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.explore.ExploreScreenState.GenreUiState

data class HistoryScreenState(
    val moviesGenres: List<GenreUiState> = emptyList(),
    val seriesGenres: List<GenreUiState> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val isContentEmpty: Boolean = false,
    val shouldShowError: Boolean = false,
    val youRecentlyViewed: List<MediaItemUiState> = emptyList(),
)