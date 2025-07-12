package com.repository.mapper

import com.android.domain.model.Series
import com.local.entity.SeriesEntity
import com.utils.IMAGES_URL

fun List<SeriesEntity>.toDomain(): List<Series> {
    return map{
        Series(
            id = it.id,
            name = it.name,
            rating = it.rating,
            adult =false,
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