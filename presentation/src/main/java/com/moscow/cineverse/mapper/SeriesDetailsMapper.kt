package com.moscow.cineverse.mapper

import com.moscow.cineverse.common_ui_state.CrewUiState
import com.moscow.cineverse.screen.series_details.SeasonUiState
import com.moscow.cineverse.screen.series_details.SeriesDetailsUiState
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
    releaseDate = releaseDate?.toFormattedReleasedDate().toString(),
    posterPath = posterPath,
    numberOfSeasons = numberOfSeasons,
    numberOfEpisodes = numberOfEpisodes,
    seasons = seasons.map { it.toUi() },
    creators = creators.map { it.toUi() }
)

fun Season.toUi() = SeasonUiState(
    id = id,
    title = name,
    airDate = airDate?.toFormattedReleasedDate().toString(),
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