package com.moscow.mapper

import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series
import com.moscow.local.entity.HomeItemEntity

const val ITEM_MOVIE = "movie"
const val ITEM_SERIES = "series"

fun Movie.toHomeItemEntity(categoryType: String): HomeItemEntity {
    return HomeItemEntity(
        id = this.id,
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

fun Series.toHomeItemEntity(categoryType: String): HomeItemEntity {
    return HomeItemEntity(
        id = this.id,
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

fun HomeItemEntity.toMovie(
    adult: Boolean = false,
    originalLanguage: String = "",
    originalTitle: String = "",
    overview: String = "",
    video: Boolean = false
): Movie {
    return Movie(
        id = this.id,
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

fun HomeItemEntity.toSeries(
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