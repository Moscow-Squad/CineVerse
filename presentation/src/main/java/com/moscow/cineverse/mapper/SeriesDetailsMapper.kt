package com.moscow.cineverse.mapper

import com.moscow.cineverse.common_ui_state.CrewUiState
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.series_details.SeasonUiState
import com.moscow.cineverse.screen.series_details.SeriesDetailsUiState
import com.moscow.domain.model.MediaType
import com.moscow.domain.model.Series
import com.moscow.domain.model.details.Creator
import com.moscow.domain.model.details.Season
import com.moscow.domain.model.details.SeriesDetail

fun SeriesDetail.toUi() = SeriesDetailsUiState(
    id = id,
    title = title,
    overview = overview,
    rating = rating.toString(),
    genre = genres.joinToString(", ") { it.name },
    duration = runtime,
    releaseDate = releaseDate.toFormattedReleasedDate().toString(),
    posterPath = posterPath,
    numberOfSeasons = numberOfSeasons,
    numberOfEpisodes = numberOfEpisodes,
    seasons = seasons.map { it.toUi() },
    creators = creators.map { it.toUi() }
)

fun Season.toUi() = SeasonUiState(
    id = id,
    title = name,
    airDate = airDate.toFormattedReleasedDate().toString(),
    episodeCount = episodeCount,
    posterPath = posterPath,
    overview = overview,
    rate = rate.toString()
)

fun Creator.toUi() = CrewUiState(
    id = id,
    name = name,
    job = "Creator"
)

fun Series.toUi() = MediaItemUiState(
    id = id,
    title = name,
    posterPath = posterPath,
    rating = rating,
    genres = emptyList(),
    releaseDate = firstAirDate.toString(),
    duration = "",
    mediaType = MediaType.Tv,
    backdropPath = this.backdropPath
)