package com.moscow.cineverse.screen.see_more

import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.domain.model.MediaItem
import com.moscow.domain.model.MediaType
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series

internal fun movieToUiState(movie: Movie): MediaItemUiState {
    return MediaItemUiState(
        id = movie.id.toInt(),
        title = movie.name,
        posterPath = movie.posterPath,
        rating = movie.rating,
        genres = emptyList(), // We don't have genre details in this context, so use empty list to avoid crashes
        releaseDate = movie.releaseDate.toString(),
        duration = "",
        backdropPath = movie.backdropPath,
        mediaType = MediaType.Movie
    )
}

internal fun seriesToUiState(series: Series): MediaItemUiState {
    return MediaItemUiState(
        id = series.id,
        title = series.name,
        posterPath = series.posterPath,
        rating = series.rating,
        genres = emptyList(), // We don't have genre details in this context, so use empty list to avoid crashes
        releaseDate = series.firstAirDate.toString(),
        duration = "",
        backdropPath = series.backdropPath,
        mediaType = MediaType.Tv
    )
}

