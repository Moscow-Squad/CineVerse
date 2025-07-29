package com.moscow.mapper

import com.moscow.domain.model.CastDetails
import com.moscow.domain.model.CreditsDetails
import com.moscow.domain.model.CrewDetails
import com.moscow.domain.model.Series
import com.moscow.domain.model.details.Creator
import com.moscow.domain.model.details.Episode
import com.moscow.domain.model.details.ListOfSeries
import com.moscow.domain.model.details.Season
import com.moscow.domain.model.details.SeriesDetail
import com.moscow.domain.model.details.SeriesItem
import com.moscow.remote.dto.details.CreatedByDto
import com.moscow.remote.dto.details.LastEpisodeToAirDto
import com.moscow.remote.dto.details.SeriesCastDto
import com.moscow.remote.dto.details.SeriesCreditDto
import com.moscow.remote.dto.details.SeriesCrewDto
import com.moscow.remote.dto.details.SeriesRecommendationDto
import com.moscow.remote.dto.series.ListOfSeriesDto
import com.moscow.remote.dto.series.SeasonDto
import com.moscow.remote.dto.series.SeriesDetailDto
import com.moscow.remote.dto.series.SeriesItemDto
import com.moscow.utils.IMAGES_URL
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.collections.map

fun SeriesDetailDto.toDomain(): SeriesDetail {
    return SeriesDetail(
        id = id,
        title = name,
        overview = overview,
        posterPath = IMAGES_URL + posterPath.orEmpty(),
        genres = genres.map { it.toDomain() },
        rating = (voteAverage * 10).toInt() / 10.0,
        runtime = formatRuntime(episodeRunTime) ?: "0m",
        releaseDate = if (firstAirDate == null) Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        else LocalDate.parse(firstAirDate),
        type = type,
        creators = createdBy.map { it.toDomain() },
        numberOfSeasons = numberOfSeasons,
        numberOfEpisodes = numberOfEpisodes,
        seasons = seasons.map { it.toDomain() },
        backdropPath = IMAGES_URL + backdropPath,
        voteCount = voteCount
    )
}

private fun CreatedByDto.toDomain(): Creator {
    return Creator(
        id = id,
        name = name ?: "",
        profilePath = profilePath ?: ""
    )
}

internal fun SeasonDto.toDomain(): Season {
    return Season(
        id = id,
        name = name ?: "",
        airDate = airDate ?: "",
        episodeCount = episodeCount ?: 0,
        posterPath = IMAGES_URL + posterPath.orEmpty(),
        overview = overview ?: "",
        rate = voteAverage?.toFloat() ?: 0f
    )
}

fun ListOfSeriesDto.toDomain(): ListOfSeries {
    return ListOfSeries(
        id = id,
        page = page,
        results = results.map { it.toDomain() },
        totalPages = totalPages,
        totalResults = totalResults
    )
}

fun SeriesItemDto.toDomain(): SeriesItem {
    return SeriesItem(
        id = id,
        name = name,
        description = description,
        favoriteCount = favoriteCount,
        itemCount = itemCount,
        iso6391 = iso6391,
        iso31661 = iso31661,
        posterPath = posterPath
    )
}

fun SeriesCreditDto.toDomain() = CreditsDetails(
    actors = cast.map { it.toDomain() },
    behindTheScene = crew.map { it.toDomain() }
)

fun SeriesCastDto.toDomain() = CastDetails(
    id = id,
    originalName = originalName ?: "",
    characterName = roles.firstOrNull()?.character ?: "",
    profileImg = IMAGES_URL + profilePath
)
fun SeriesCrewDto.toDomain() = CrewDetails(
    id = id,
    name = originalName ?: "",
    job = jobs.firstOrNull()?.job ?: "",
    profileImage = IMAGES_URL + profilePath
)

fun SeriesRecommendationDto.toDomain() = Series(
    id = id,
    name = name ?: "",
    rating = voteAverage?.toFloat() ?: 0f,
    adult = adult ?: false,
    backdropPath = IMAGES_URL + backdropPath.orEmpty(),
    firstAirDate = if (firstAirDate != null){
        LocalDate.parse(firstAirDate)
    } else  {
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    },
    genreIds = genreIds,
    originCountry = originCountry,
    originalLanguage = originalLanguage ?: "",
    originalName = originalName ?: "",
    overview = overview ?: "",
    posterPath = IMAGES_URL + posterPath.orEmpty()
)

private fun formatRuntime(runtimeMinutes: List<Int>): String? {
    if (runtimeMinutes.isEmpty()) return null
    val totalMinutes = runtimeMinutes.firstOrNull() ?: return null
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60

    return when {
        hours > 0 && minutes > 0 -> "${hours}h ${minutes}m"
        hours > 0 -> "${hours}h"
        else -> "${minutes}m"
    }
}