package com.repository.mapper

import com.android.domain.model.Movie
import com.local.entity.MovieEntity
import kotlinx.datetime.LocalDate

fun List<MovieEntity>.toDomain(): List<Movie>  {
    return map { movieEntity ->
        Movie(
            id = movieEntity.id,
            name = movieEntity.name,
            genreIds = movieEntity.genresId,
            overview = "",
            rating = movieEntity.rating,
            releaseDate = LocalDate.parse(movieEntity.releaseDate),
            poster = movieEntity.poster,
            duration = movieEntity.duration,
            adult = false,
            backdropPath = "",
            originalLanguage = "en",
            originalTitle = "",
            posterPath = "",
            video = true
        )
    }
}

fun List<Movie>.toEntity(searchTerm: String): List<MovieEntity> {
    return map { movie ->
        MovieEntity(
            id = movie.id,
            name = movie.name,
            genresId = movie.genreIds,
            rating = movie.rating,
            releaseDate = movie.releaseDate.toString(),
            poster = movie.poster,
            duration = movie.duration,
            searchTerm = searchTerm
        )
    }
}