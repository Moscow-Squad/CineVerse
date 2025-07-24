package com.moscow.cineverse.screen.castDetails.best0fmovies

import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.designSystem.component.ViewMode

data class ShowAllActorMoviesState(
    val actorId: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val viewMode: ViewMode = ViewMode.GRID,
    val movies: List<MediaItemUiState> = emptyList(),
    val moviesGenres: List<GenreUi> = emptyList(),
)

data class GenreUi(
    val id: Int,
    val name: String
)