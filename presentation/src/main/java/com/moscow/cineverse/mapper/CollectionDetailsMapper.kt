package com.moscow.cineverse.mapper

import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.explore.ExploreScreenState.GenreUiState
import com.moscow.domain.model.MediaItem
import com.moscow.domain.model.MediaType

fun MediaItem.toUi(movieGenre: List<GenreUiState>, seriesGenre: List<GenreUiState>) = MediaItemUiState(
    id = id,
    title = name,
    posterPath = posterPath,
    rating = rate,
    genres = if (mediaType == MediaType.Movie)
        genreIds.map { it -> movieGenre.firstOrNull { genre -> genre.id == it }?.name ?: "" }
    else if (mediaType == MediaType.Tv)
        genreIds.map { it -> seriesGenre.firstOrNull { genre -> genre.id == it }?.name ?: "" }
    else listOf(),
    releaseDate = firstAirDate,
    duration = "",
    mediaType = mediaType,
    backdropPath = backdropPath
)