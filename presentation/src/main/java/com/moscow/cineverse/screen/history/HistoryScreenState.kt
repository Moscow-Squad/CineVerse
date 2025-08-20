package com.moscow.cineverse.screen.history

import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.explore.ExploreScreenState.GenreUiState

data class HistoryScreenState(
    val moviesGenres: List<GenreUiState> = emptyList(),
    val seriesGenres: List<GenreUiState> = emptyList(),
    val isLoading: Boolean = false,
    val showTip: Boolean = true,
    val errorMessage: Int? = null,
    val isError: Boolean = false,
    val isContentEmpty: Boolean = false,
    val shouldShowError: Boolean = false,
    val enableBlur: String = "high",
    val youRecentlyViewed: List<MediaItemUiState> = emptyList(),
)