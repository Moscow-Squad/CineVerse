package com.moscow.cineverse.screen.details.movie_details.recommendations

import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.explore.ExploreScreenState.GenreUiState
import com.moscow.cineverse.utlis.ViewMode

data class RecommendationsMoviesState(
    val movieId: Int = 0,
    val movieTitle: String = "",
    val isLoading: Boolean = false,
    val error: Int? = null,
    val viewMode: ViewMode = ViewMode.GRID,
    val movies: List<MediaItemUiState> = emptyList(),
    val enableBlur: String = "high",
    val moviesGenres: List<GenreUiState> = listOf(),
)