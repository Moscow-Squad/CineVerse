package com.moscow.cineverse.mapper

import com.android.domain.model.MediaType
import com.android.domain.model.Series
import com.android.domain.model.details.Creator
import com.android.domain.model.details.Season
import com.android.domain.model.details.SeriesDetail
import com.moscow.cineverse.common_ui_state.CrewUiState
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.series_details.SeasonUiState
import com.moscow.cineverse.screen.series_details.SeriesDetailsUiState

fun SeriesDetail.toUi() = SeriesDetailsUiState(
    id = id,
    title = title,
    overview = overview,
    rating = rating.toString(),
    genre = genres.joinToString(", ") { it.name },
    duration = runtime,
    releaseDate = releaseDate.toString(),
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
    mediaType = MediaType.Tv
)