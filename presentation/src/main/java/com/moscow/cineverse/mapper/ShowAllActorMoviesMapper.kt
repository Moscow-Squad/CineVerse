package com.moscow.cineverse.mapper

import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.castDetails.best0fmovies.GenreUi
import com.moscow.cineverse.screen.explore.YYYY_MMM_DD
import com.moscow.cineverse.screen.explore.formatWith
import com.moscow.domain.model.Genre
import com.moscow.domain.model.MediaType
import com.moscow.domain.model.Movie
import kotlin.collections.map

fun List<Movie>.toUi(
    genresList: List<GenreUi>
): List<MediaItemUiState> {
    return this.map { movie ->
        MediaItemUiState(
            id = movie.id,
            title = movie.name,
            posterPath = movie.posterPath,
            rating = movie.rating,
            genres = movie.genreIds.map { genresList.first { genre -> genre.id == it }.name },
            releaseDate = movie.releaseDate.formatWith(YYYY_MMM_DD) ?: "",
            duration = "",
            mediaType = MediaType.Movie
        )
    }
}

fun Genre.toUi() =
    GenreUi(
        id = id,
        name = name
    )