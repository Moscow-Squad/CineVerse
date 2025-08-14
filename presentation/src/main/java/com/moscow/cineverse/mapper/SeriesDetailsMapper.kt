package com.moscow.cineverse.mapper

import com.moscow.cineverse.common_ui_state.CrewUiState
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.details.series_details.SeasonUiState
import com.moscow.cineverse.screen.details.series_details.SeriesDetailsUiState

import com.moscow.domain.model.Series
import com.moscow.domain.model.Series.Creator
import com.moscow.domain.model.Series.Season

fun Series.toUi() = SeriesDetailsUiState(
    id = id,
    title = title,
    overview = overview,
    trailerPath = trailerPath,
    rating = rating.toString(),
    genre = genres.joinToString(", ") { it.name },
    releaseDate = releaseDate,
    posterPath = posterPath,
    numberOfSeasons = numberOfSeasons,
    numberOfEpisodes = numberOfEpisodes,
    seasons = seasons.map { it.toUi() },
    creators = creators.map { it.toUi() }
)

fun Season.toUi() = SeasonUiState(
    id = id,
    title = name,
    airDate = airDate,
    episodeCount = episodeCount,
    posterPath = posterPath,
    overview = overview,
    rate = rate
)

fun Creator.toUi() = CrewUiState(
    id = id,
    name = name,
    job = "Creator"
)

fun Series.toMediaItemUi() =
    MediaItemUiState(
        id = id,
        title = title,
        posterPath = posterPath,
        rating = rating,
        genres = genres.map { it.name },
        releaseDate = releaseDate,
        backdropPath = backdropPath,
        mediaType = MediaItemUiState.MediaType.Series,
    )