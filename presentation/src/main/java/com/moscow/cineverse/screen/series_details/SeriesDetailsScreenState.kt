package com.moscow.cineverse.screen.series_details


import com.android.domain.model.CastDetails
import com.android.domain.model.CrewDetails
import com.android.domain.model.MediaType
import com.android.domain.model.Review
import com.android.domain.model.Series
import com.android.domain.model.details.Creator
import com.android.domain.model.details.Season
import com.android.domain.model.details.SeriesDetail
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.movie_details.MovieScreenState.ReviewUi

data class SeriesDetailsScreenState(
    val isLoading: Boolean = false,
    val seriesDetail: SeriesDetailsUiState = SeriesDetailsUiState(),
    val reviews: List<ReviewUi> = emptyList(),
    val cast: List<CastUiState> = emptyList(),
    val crew: List<CrewUiState> = emptyList(),
    val recommendation: List<MediaItemUiState> = emptyList(),
    val shouldShowError: Boolean = false,
    val errorMessage: String = "",
    val latestSeason: List<SeriesDetail> = emptyList(),
    val listOfSeries: List<SeriesDetail> = emptyList(),
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
    val creators: List<CrewUiState> = emptyList()
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

data class CrewUiState(
    val id: Int = 0,
    val name: String = "",
    val profilePath: String = "",
    val job: String = "",
)

data class CastUiState(
    val id: Int = 0,
    val name: String = "",
    val profilePath: String = "",
    val characterName: String = "",
)

data class SeriesRecommendation(
    val id: Int = 0,
    val title: String = "",
    val posterPath: String = "",
    val rating: Float = 0f,
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
    seasons = seasons.map { it.ui() }.reversed(),
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

fun Creator.ui() = CrewUiState(
    id = id,
    name = name,
    profilePath = profilePath,
    job = "Creator"
)

fun CastDetails.ui() = CastUiState(
    id = id,
    name = originalName,
    profilePath = profileImg,
    characterName = characterName
)

fun CrewDetails.ui() = CrewUiState(
    id = id,
    name = name,
    profilePath = profileImage,
    job = job
)

fun Series.ui() = MediaItemUiState(
    id = id,
    title = name,
    posterPath = posterPath,
    rating = rating,
    genres = emptyList(),
    releaseDate = firstAirDate.toString(),
    duration = "",
    mediaType = MediaType.Tv
)