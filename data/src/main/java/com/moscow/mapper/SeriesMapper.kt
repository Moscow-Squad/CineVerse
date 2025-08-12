package com.moscow.mapper

import com.moscow.domain.model.Genre
import com.moscow.domain.model.Series
import com.moscow.local.entity.HistoryItemEntity
import com.moscow.local.entity.MediaItemEntity
import com.moscow.remote.dto.GenreDto
import com.moscow.remote.dto.series.SeriesDto
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
