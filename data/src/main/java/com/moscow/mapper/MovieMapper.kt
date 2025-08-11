package com.moscow.mapper

import com.moscow.domain.model.Movie
import com.moscow.local.entity.MovieEntity
import com.moscow.utils.IMAGES_URL
import kotlinx.datetime.LocalDate
import kotlin.collections.map

fun List<MovieEntity>.toDomain(): List<Movie> {
    return map { movieEntity ->
        Movie(
            id = movieEntity.id.toInt(),
            name = movieEntity.name,
            genreIds = movieEntity.genresId,
            rating = movieEntity.rating,
            releaseDate = LocalDate.parse(movieEntity.releaseDate),
            poster = movieEntity.poster,
            adult = false,
            posterPath = IMAGES_URL + movieEntity.poster,
            video = true
        )
    }
}
