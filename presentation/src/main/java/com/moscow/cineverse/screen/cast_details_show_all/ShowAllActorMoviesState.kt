package com.moscow.cineverse.screen.cast_details_show_all

import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.screen.component.movie_poster_card.MediaItemUi

data class ShowAllActorMoviesState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val viewMode: ViewMode = ViewMode.GRID,
    val movies: List<MediaItemUi> = emptyList()
)
