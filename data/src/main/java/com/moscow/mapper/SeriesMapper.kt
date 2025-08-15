package com.moscow.mapper


import com.moscow.domain.model.Genre
import com.moscow.domain.model.Series
import com.moscow.domain.model.Series.Creator
import com.moscow.domain.model.Series.Season
import com.moscow.local.entity.HistoryItemEntity
import com.moscow.local.entity.MediaItemEntity
import com.moscow.remote.dto.genre.GenreDto
import com.moscow.remote.dto.media_item.common.SeriesRecommendationDto
import com.moscow.remote.dto.media_item.series.CreatedByDto
import com.moscow.remote.dto.media_item.series.SeasonDto
import com.moscow.remote.dto.media_item.series.SeriesDetailDto
import com.moscow.remote.dto.media_item.series.SeriesDto
import com.moscow.utils.IMAGES_URL
import kotlinx.datetime.LocalDate

const val ITEM_SERIES = "series"

fun Series.toHomeItemEntity(categoryType: String): MediaItemEntity {
    return MediaItemEntity(
        itemId = this.id,
        categoryType = categoryType,
        name = this.title,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        rating = rating,
        genreIds = genreIds,
        releaseDate = releaseDate,
        itemType = ITEM_SERIES
    )
}


fun Series.toHistoryItemEntity(): HistoryItemEntity {
    return HistoryItemEntity(
        id = this.id,
        name = this.title,
        posterPath = this.posterPath,
        rating = rating,
        releaseDate = releaseDate,
        itemType = ITEM_SERIES
    )
}


fun MediaItemEntity.toSeries(
    overview: String = ""
): Series {
    return Series(
        id = this.itemId,
        title = this.name,
        overview = overview,
        posterPath = this.posterPath,
        trailerPath = "",
        backdropPath = this.posterPath,
        genres = emptyList(),
        genreIds = emptyList(),
        rating = this.rating,
        voteCount = 0,
        releaseDate = this.releaseDate,
        type = "",
        creators = emptyList(),
        numberOfSeasons = 0,
        numberOfEpisodes = 0,
        seasons = emptyList()
    )
}


fun HistoryItemEntity.toSeries(
    overview: String = ""
): Series {
    return Series(
        id = this.id,
        title = this.name,
        overview = overview,
        posterPath = this.posterPath,
        trailerPath = "",
        backdropPath = this.posterPath,
        genres = emptyList(),
        genreIds = emptyList(),
        rating = this.rating,
        voteCount = 0,
        releaseDate = this.releaseDate,
        type = "",
        creators = emptyList(),
        numberOfSeasons = 0,
        numberOfEpisodes = 0,
        seasons = emptyList()
    )
}

fun SeriesDto.toDomain() =
    Series(
        id = id ?: 0,
        title = name ?: "",
        overview = overview.orEmpty(),
        posterPath = IMAGES_URL + posterPath.orEmpty(),
        trailerPath = "",
        backdropPath = IMAGES_URL + backdropPath.orEmpty(),
        genres = emptyList(),
        genreIds = genreIds ?: emptyList(),
        rating = voteAverage ?: 0f,
        voteCount = voteCount ?: 0,
        releaseDate = if (!firstAirDate.isNullOrBlank()) LocalDate.parse(firstAirDate) else null,
        type = "",
        creators = emptyList(),
        numberOfSeasons = 0,
        numberOfEpisodes = 0,
        seasons = emptyList()
    )


fun GenreDto.toDomain() =
    Genre(
        id = id,
        name = name
    )

fun SeriesDetailDto.toDomain(trailer: String): Series {
    return Series(
        id = id,
        title = name,
        overview = overview,
        posterPath = IMAGES_URL + posterPath.orEmpty(),
        trailerPath = "https://youtu.be/$trailer",
        genres = genres.map { it.toDomain() },
        rating = voteAverage.toFloat(),
        releaseDate = if (firstAirDate.isNullOrBlank()) null else LocalDate.parse(firstAirDate),
        type = type,
        creators = createdBy.map { it.toDomain() },
        numberOfSeasons = numberOfSeasons,
        numberOfEpisodes = numberOfEpisodes,
        seasons = seasons.map { it.toDomain() },
        backdropPath = IMAGES_URL + backdropPath,
        voteCount = voteCount,
        genreIds = emptyList(),
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
        airDate = if (airDate.isNullOrBlank()) null else LocalDate.parse(airDate),
        episodeCount = episodeCount ?: 0,
        posterPath = IMAGES_URL + posterPath.orEmpty(),
        overview = overview ?: "",
        rate = voteAverage?.toFloat() ?: 0f
    )
}



fun SeriesRecommendationDto.toDomain() = Series(
    id = id,
    title = name ?: "",
    overview = overview ?: "",
    posterPath = IMAGES_URL + posterPath.orEmpty(),
    trailerPath = "",
    backdropPath = IMAGES_URL + backdropPath.orEmpty(),
    genres = emptyList(),
    genreIds = genreIds,
    rating = voteAverage?.toFloat() ?: 0f,
    voteCount = 0,
    releaseDate = if (firstAirDate.isNullOrBlank()) null else LocalDate.parse(firstAirDate),
    creators = emptyList(),
    numberOfSeasons = 0,
    numberOfEpisodes = 0,
    seasons = emptyList(),
    type = ""
)
