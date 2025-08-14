package com.moscow.cineverse.mapper

import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.explore.ExploreScreenState.GenreUiState
import com.moscow.domain.model.Movie

fun List<Movie>.toUi(
    genresList: List<GenreUiState>
): List<MediaItemUiState> {
    return this.map { movie ->
        MediaItemUiState(
            id = movie.id,
            title = movie.title,
            posterPath = movie.posterUrl,
            rating = movie.rating,
            genres = movie.genreIds.map { genresList.first { genre -> genre.id == it }.name },
            releaseDate = movie.releaseDate,
            backdropPath = movie.backdropUrl,
            mediaType = MediaItemUiState.MediaType.Movie,
            duration = movie.duration.toUi()
        )
    }
}