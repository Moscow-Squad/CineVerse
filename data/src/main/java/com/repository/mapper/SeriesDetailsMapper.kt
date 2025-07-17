package com.repository.mapper

import com.android.domain.model.Creator
import com.android.domain.model.Episode
import com.android.domain.model.Genre
import com.android.domain.model.Season
import com.android.domain.model.SeriesDetail
import com.remote.dto.GenreDto
import com.remote.dto.details.CreatedByDto
import com.remote.dto.details.LastEpisodeToAirDto
import com.remote.dto.details.SeasonDto
import com.remote.dto.details.SeriesDetailDto
import java.text.SimpleDateFormat
import java.util.Locale

fun SeriesDetailDto.toDomain(): SeriesDetail {
    return SeriesDetail(
        id = id,
        title = name,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        genres = genres.map { it.toDomain() },
        rating = voteAverage,
        voteCount = voteCount,
        runtime = formatRuntime(episodeRunTime),
        releaseDate = formatDate(firstAirDate),
        type = type,
        cast = emptyList(),
        creators = createdBy.map { it.toDomain() },
        tagline = tagline,
        status = status,
        numberOfSeasons = numberOfSeasons,
        numberOfEpisodes = numberOfEpisodes,
        lastAirDate = lastAirDate,
        nextAirDate = nextEpisodeToAir,
        lastEpisodeToAir = lastEpisodeToAir?.toDomain(),
        nextEpisodeToAir = null,
        reviews = emptyList(),
        similarSeries = emptyList(),
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
        airDate = airDate,
        episodeCount = episodeCount,
        posterPath = posterPath
    )
}

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

private fun formatDate(dateString: String?): String? {
    if (dateString.isNullOrEmpty()) return null
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy, MMM dd", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) }
    } catch (e: Exception) {
        dateString
    }
}
