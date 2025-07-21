package com.mapper

import com.android.domain.model.details.Creator
import com.android.domain.model.details.Episode
import com.android.domain.model.details.ListOfSeries
import com.android.domain.model.details.Season
import com.android.domain.model.details.SeriesDetail
import com.android.domain.model.details.SeriesItem
import com.remote.dto.details.CreatedByDto
import com.remote.dto.details.LastEpisodeToAirDto
import com.remote.dto.details.ListOfSeriesDto
import com.remote.dto.details.SeasonDto
import com.remote.dto.details.SeriesDetailDto
import com.remote.dto.details.SeriesItemDto

fun SeriesDetailDto.toDomain(): SeriesDetail {
    return SeriesDetail(
        id = id,
        title = name,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        genres = genres?.map { it.toDomain() }?:emptyList(),
        rating = voteAverage,
        voteCount = voteCount,
        releaseDate = firstAirDate,
        type = type,
        cast = emptyList(),
        creators = createdBy?.map { it.toDomain() }?:emptyList(),
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
        seasons = seasons?.map { it.toDomain() }?:emptyList()
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

internal fun SeasonDto.toDomain(): Season {
    return Season(
        id = id,
        name = name,
        airDate = airDate,
        episodeCount = episodeCount,
        posterPath = posterPath
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

