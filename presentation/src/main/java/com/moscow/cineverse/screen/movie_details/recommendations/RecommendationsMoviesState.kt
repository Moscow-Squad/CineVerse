package com.moscow.cineverse.screen.movie_details.recommendations

import androidx.paging.PagingData
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.explore.ExploreScreenState.GenreUiState
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class RecommendationsMoviesState(
    val movieId: Int = 0,
    val movieTitle: String = "",
    val isLoading: Boolean = false,
    val error: Int? = null,
    val viewMode: ViewMode = ViewMode.GRID,
    val movies: List<MediaItemUiState> = emptyList(),
    val enableBlur: String = "high",
    val moviesGenres: List<GenreUiState> = listOf(),
    val recommendation: Flow<PagingData<MediaItemUiState>> = flowOf()
)