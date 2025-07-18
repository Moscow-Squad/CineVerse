package com.moscow.cineverse.screen.castDetails.best0fmovies

import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.screen.model.MediaItemUi

data class ShowAllActorMoviesState(
    val actorId: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val viewMode: ViewMode = ViewMode.GRID,
    val movies: List<MediaItemUi> = emptyList(),
    val moviesGenres: List<GenreUi> = emptyList(),
)
data class GenreUi(
    val id: Int,
    val name: String
)