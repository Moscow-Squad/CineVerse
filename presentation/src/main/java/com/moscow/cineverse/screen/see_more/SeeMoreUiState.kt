package com.moscow.cineverse.screen.see_more

import com.moscow.cineverse.screen.explore.ExploreScreenState.GenreUiState
import com.moscow.cineverse.utlis.ViewMode

data class SeeMoreUiState(
    val title: Int = 0,
    val isLoading: Boolean = false,
    val isContentEmpty: Boolean = false,
    val shouldShowError: Boolean = false,
    val viewMode: ViewMode = ViewMode.GRID,
    val enableBlur: Boolean = true,
    val moviesGenres: List<GenreUiState> = listOf(),
    val seriesGenres: List<GenreUiState> = listOf()
)
