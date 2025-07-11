package com.repository.mapper

import com.android.domain.model.Movie
import com.local.entity.MovieEntity
import kotlinx.datetime.LocalDate

fun List<MovieEntity>.toDomain(): List<Movie>  {
    return map { movieEntity ->
        Movie(
            id = movieEntity.id,
            name = movieEntity.name,
            genresId = movieEntity.genresId,
            description = "",
            rating = movieEntity.rating,
            releaseDate = LocalDate.parse(movieEntity.releaseDate),
            poster = movieEntity.poster,
            duration = movieEntity.duration
        )
    }
}

fun List<Movie>.toEntity(searchTerm: String): List<MovieEntity> {
    return map { movie ->
        MovieEntity(
            id = movie.id,
            name = movie.name,
            genresId = movie.genresId,
            rating = movie.rating,
            releaseDate = movie.releaseDate.toString(),
            poster = movie.poster,
            duration = movie.duration,
            searchTerm = searchTerm
        )
    }
}