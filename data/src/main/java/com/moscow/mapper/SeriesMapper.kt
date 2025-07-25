package com.moscow.mapper

import com.moscow.domain.model.Series
import com.moscow.local.entity.SeriesEntity
import kotlin.collections.map

fun List<SeriesEntity>.toDomain(): List<Series> {
    return map {
        Series(
            id = it.id,
            name = it.name,
            rating = it.rating,
            adult = false,
            backdropPath = "",
            firstAirDate = it.releaseDate,
            genreIds = it.genresId,
            originCountry = emptyList(),
            originalLanguage = "",
            originalName = "",
            overview = it.description,
            posterPath = it.poster
        )
    }
}

fun List<Series>.toEntity(searchTerm: String): List<SeriesEntity> {
    return map {
        SeriesEntity(
            id = it.id,
            searchTerm = searchTerm,
            name = it.name,
            genresId = it.genreIds,
            description = it.overview,
            rating = it.rating,
            releaseDate = it.firstAirDate,
            poster = it.posterPath
        )
    }
}
