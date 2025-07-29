package com.moscow.cineverse.screen.cast_details.best_of_movies

import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.designSystem.component.ViewMode

data class ShowAllActorMoviesState(
    val actorId: Int = 0,
    val actorName: String = "",
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