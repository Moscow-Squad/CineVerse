package com.moscow.cineverse.screen.movie_details.recommendations

import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.screen.component.movie_poster_card.MediaItemUi

data class RecommendationsMoviesState(
    val movieId: Int = 0,
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