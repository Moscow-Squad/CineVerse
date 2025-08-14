package com.moscow.cineverse.mapper

import com.moscow.cineverse.common_ui_state.CollectionUiState
import com.moscow.cineverse.common_ui_state.DurationUiState
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.home.HomeUiState
import com.moscow.domain.model.Collection
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series

fun List<Movie>.toUi(
    genresList: List<HomeUiState.GenreUi> = listOf()
): List<MediaItemUiState> {
    return this.map { movie ->
        MediaItemUiState(
            id = movie.id,
            title = movie.title,
            posterPath = movie.posterUrl,
            backdropPath = movie.backdropUrl,
            rating = movie.rating,
            genres = if (genresList.isEmpty()) listOf() else
                movie.genreIds.map { genresList.first { genre -> genre.id == it }.name },
            releaseDate = movie.releaseDate,
            mediaType = MediaItemUiState.MediaType.Movie,
            duration = movie.duration.toUi()

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
            releaseDate = series.releaseDate,
            backdropPath = series.backdropPath,
            mediaType = MediaItemUiState.MediaType.Series,
            duration = DurationUiState(0, 0)
        )
    }
}

fun Collection.toUi() = CollectionUiState(
    id = id,
    title = name,
    numberOfItems = itemCount,
    isLoading = false
)