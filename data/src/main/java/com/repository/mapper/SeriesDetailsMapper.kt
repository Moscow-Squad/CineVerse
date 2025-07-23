package com.repository.mapper

import com.android.domain.model.CastDetails
import com.android.domain.model.CreditsDetails
import com.android.domain.model.CrewDetails
import com.android.domain.model.Genre
import com.android.domain.model.Series
import com.android.domain.model.details.Creator
import com.android.domain.model.details.Episode
import com.android.domain.model.details.ListOfSeries
import com.android.domain.model.details.Season
import com.android.domain.model.details.SeriesDetail
import com.android.domain.model.details.SeriesItem
import com.remote.dto.GenreDto
import com.remote.dto.details.CreatedByDto
import com.remote.dto.details.LastEpisodeToAirDto
import com.remote.dto.details.ListOfSeriesDto
import com.remote.dto.details.SeasonDto
import com.remote.dto.details.SeriesCastDto
import com.remote.dto.details.SeriesCreditDto
import com.remote.dto.details.SeriesCrewDto
import com.remote.dto.details.SeriesDetailDto
import com.remote.dto.details.SeriesItemDto
import com.remote.dto.details.SeriesRecommendationDto
import com.utils.IMAGES_URL
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun SeriesDetailDto.toDomain(): SeriesDetail {
    return SeriesDetail(
        id = id,
        title = name,
        overview = overview,
        posterPath = IMAGES_URL + posterPath.orEmpty(),
        genres = genres.map { it.toDomain() },
        rating = voteAverage.toFloat(),
        runtime = formatRuntime(episodeRunTime).toString(),
        releaseDate = if (firstAirDate != null){
            LocalDate.parse(firstAirDate)
        } else  {
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        },
        type = type,
        creators = createdBy.map { it.toDomain() },
        numberOfSeasons = numberOfSeasons,
        numberOfEpisodes = numberOfEpisodes,
        seasons = seasons.map { it.toDomain() }
    )
}

private fun GenreDto.toDomain(): Genre {
    return Genre(
        id = id,
        name = name
    )
}

private fun CreatedByDto.toDomain(): Creator {
    return Creator(
        id = id,
        name = name,
        profilePath = profilePath
    )
}

private fun LastEpisodeToAirDto.toDomain(): Episode {
    return Episode(
        id = id,
        name = name,
        overview = overview,
        airDate = airDate,
        episodeNumber = episodeNumber,
        seasonNumber = seasonNumber
    )
}

private fun SeasonDto.toDomain(): Season {
    return Season(
        id = id,
        name = name,
        airDate = airDate ?: "",
        episodeCount = episodeCount,
        posterPath = IMAGES_URL + posterPath.orEmpty(),
        overview = overview,
        rate = voteAverage.toFloat()
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
