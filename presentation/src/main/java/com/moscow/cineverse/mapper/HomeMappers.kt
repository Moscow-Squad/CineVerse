package com.moscow.cineverse.mapper

import com.moscow.cineverse.common_ui_state.CollectionUiState
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.explore.YYYY_MMM_DD
import com.moscow.cineverse.screen.explore.formatWith
import com.moscow.cineverse.screen.home.HomeScreenState
import com.moscow.domain.model.Collection
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series

fun List<Movie>.toUi(
    genresList: List<HomeScreenState.GenreUi> = listOf()
): List<MediaItemUiState> {
    return this.map { movie ->
        MediaItemUiState(
            id = movie.id,
            title = movie.title,
            posterPath = movie.posterUrl,
            backdropPath = movie.backdropUrl,
            rating = movie.rating,
            mediaType = MediaItemUiState.MediaType.MOVIE,
            genres = if (genresList.isEmpty()) listOf() else
                movie.genreIds.map { genresList.first { genre -> genre.id == it }.name },
            releaseDate = movie.releaseDate?.formatWith(YYYY_MMM_DD) ?: "",
        )
    }
}

fun List<Series>.toUi(
): List<MediaItemUiState> {
    return this.map { series ->
        MediaItemUiState(
            id = series.id,
            title = series.title,
            posterPath = series.posterPath,
            rating = series.rating,
            genres = emptyList(),
            releaseDate = series.releaseDate.toString(),
            mediaType = MediaItemUiState.MediaType.SERIES,
            backdropPath = series.backdropPath,
        )
    }
}

fun Collection.toUi() = CollectionUiState(
    id = id,
    title = name,
    numberOfItems = itemCount,
    isLoading = false
)

