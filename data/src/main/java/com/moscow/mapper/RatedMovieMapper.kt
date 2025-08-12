package com.moscow.mapper

import com.moscow.domain.model.Movie
import com.moscow.domain.usecase.movie.GetRatedMoviesUseCase.RatedMovieResult
import com.moscow.remote.dto.rating.movie.RatedMovieDto
import com.moscow.utils.IMAGES_URL
import kotlinx.datetime.toLocalDate

fun RatedMovieDto.toOutputResult(): RatedMovieResult? {
    val id = this.id ?: return null
    val title = this.title ?: return null
    val genreIds = this.genreIds?.filterNotNull() ?: emptyList()
    val voteAverage = this.voteAverage?.toFloat() ?: 0f

    val releaseDate = try {
        this.releaseDate?.toLocalDate() ?: return null
    } catch (_: Exception) {
        return null
    }
    val backdropPath = this.backdropPath ?: ""
    val overview = this.overview ?: ""
    val posterPath = (IMAGES_URL + this.posterPath)
    val rating = this.rating?.toFloat() ?: 0f

    return RatedMovieResult(
        movie = Movie(
            id = id,
            title = title,
            genreIds = genreIds,
            voteAverage = voteAverage,
            releaseDate = releaseDate,
            backdropPath = backdropPath,
            overview = overview,
            posterPath = posterPath,
            genres = emptyList(),
            duration = Movie.Duration(0, 0),
            trailerUrl = ""
        ),
        rating = rating
    )
}
