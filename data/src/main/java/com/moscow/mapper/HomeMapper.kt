package com.moscow.mapper

import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series
import com.moscow.local.entity.HistoryItemEntity
import com.moscow.local.entity.MediaItemEntity

const val ITEM_MOVIE = "movie"
const val ITEM_SERIES = "series"

fun Movie.toHomeItemEntity(categoryType: String): MediaItemEntity {
    return MediaItemEntity(
        itemId = this.id,
        categoryType = categoryType,
        name = this.name,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        itemType = ITEM_MOVIE,
        rating = this.rating,
        genreIds = this.genreIds,
        releaseDate = this.releaseDate
    )
}

fun Series.toHomeItemEntity(categoryType: String): MediaItemEntity {
    return MediaItemEntity(
        itemId = this.id,
        categoryType = categoryType,
        name = this.name,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        rating = this.rating,
        genreIds = this.genreIds,
        releaseDate = this.firstAirDate,
        itemType = ITEM_SERIES
    )
}

fun Movie.toHistoryItemEntity(): HistoryItemEntity {
    return HistoryItemEntity(
        id = this.id,
        name = this.name,
        posterPath = this.posterPath,
        itemType = ITEM_MOVIE,
        rating = this.rating,
        releaseDate = this.releaseDate
    )
}

fun Series.toHistoryItemEntity(): HistoryItemEntity {
    return HistoryItemEntity(
        id = this.id,
        name = this.name,
        posterPath = this.posterPath,
        rating = this.rating,
        releaseDate = this.firstAirDate,
        itemType = ITEM_SERIES
    )
}

fun MediaItemEntity.toMovie(
    adult: Boolean = false,
    originalLanguage: String = "",
    originalTitle: String = "",
    overview: String = "",
    video: Boolean = false
): Movie {
    return Movie(
        id = this.itemId,
        name = this.name,
        genreIds = this.genreIds,
        rating = this.rating,
        releaseDate = this.releaseDate,
        adult = adult,
        backdropPath = this.backdropPath,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = this.posterPath,
        video = video,
        poster = this.posterPath
    )
}

fun MediaItemEntity.toSeries(
    adult: Boolean = false,
    originCountry: List<String> = emptyList(),
    originalLanguage: String = "",
    originalName: String = "",
    overview: String = ""
): Series {
    return Series(
        id = this.itemId,
        name = this.name,
        rating = this.rating,
        adult = adult,
        backdropPath = this.backdropPath,
        firstAirDate = this.releaseDate,
        genreIds = this.genreIds,
        originCountry = originCountry,
        originalLanguage = originalLanguage,
        originalName = originalName,
        overview = overview,
        posterPath = this.posterPath
    )
}


fun HistoryItemEntity.toMovie(
    adult: Boolean = false,
    originalLanguage: String = "",
    originalTitle: String = "",
    overview: String = "",
    video: Boolean = false
): Movie {
    return Movie(
        id = this.id,
        name = this.name,
        rating = this.rating,
        releaseDate = this.releaseDate,
        adult = adult,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = this.posterPath,
        video = video,
        poster = this.posterPath,
        backdropPath = this.posterPath,
        genreIds = emptyList()
    )
}

fun HistoryItemEntity.toSeries(
    adult: Boolean = false,
    originCountry: List<String> = emptyList(),
    originalLanguage: String = "",
    originalName: String = "",
    overview: String = ""
): Series {
    return Series(
        id = this.id,
        name = this.name,
        rating = this.rating,
        adult = adult,
        backdropPath = this.posterPath,
        firstAirDate = this.releaseDate,
        genreIds = emptyList(),
        originCountry = originCountry,
        originalLanguage = originalLanguage,
        originalName = originalName,
        overview = overview,
        posterPath = this.posterPath
    )
}