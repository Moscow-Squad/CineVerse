package com.moscow.cineverse.screen.series_details


import com.android.domain.model.Review
import com.android.domain.model.details.CastMember
import com.android.domain.model.details.Creator
import com.android.domain.model.details.Season
import com.android.domain.model.details.SeriesDetail

data class SeriesDetailsScreenState(
    val isLoading: Boolean = false,
    val seriesDetail: SeriesDetailsUiState = SeriesDetailsUiState(),
    val reviews: List<Review> = emptyList(),
    val error: String? = null,
    val latestSeason: List<SeriesDetail> = emptyList(),
    val listOfSeries: List<SeriesDetail> = emptyList(),
    val cast: List<CastMember> = emptyList(),
    val showRatingBottomSheet: Boolean = false,
    val starsRating: Int = 0
)
data class SeriesDetailsUiState(
    val id: Int = 0,
    val title: String = "",
    val overview : String = "",
    val rating: String = "0.0",
    val genre: String = "",
    val duration: String = "",
    val releaseDate: String = "",
    val type: String = "SERIES",
    val posterPath: String = "",
    val numberOfSeasons: Int = 0,
    val numberOfEpisodes: Int = 0,
    val seasons: List<SeasonUiState> = emptyList(),
    val creators: List<CreatorUiState> = emptyList()
)
data class SeasonUiState(
    val id: Int = 0,
    val title: String = "",
    val airDate: String = "",
    val episodeCount: Int = 0,
    val posterPath: String = "",
    val overview: String = "",
    val rate: String,
)

data class CreatorUiState(
    val id: Int = 0,
    val name: String = "",
    val profilePath: String = ""
)

fun SeriesDetail.ui() = SeriesDetailsUiState(
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
    seasons = seasons.map { it.ui() },
    creators = creators.map { it.ui() }
)

fun Season.ui() = SeasonUiState(
    id = id,
    title = name,
    airDate = airDate,
    episodeCount = episodeCount,
    posterPath = posterPath,
    overview = overview,
    rate = rate.toString()
)

fun Creator.ui() = CreatorUiState(
    id = id,
    name = name,
    profilePath = profilePath
)