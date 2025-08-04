package com.moscow.mapper

import com.moscow.domain.model.Movie
import com.moscow.domain.usecase.movie.GetRatedMoviesUseCase.RatedMovieResult
import com.moscow.remote.dto.rating.movie.RatedMovieDto
import kotlinx.datetime.toLocalDate

fun RatedMovieDto.toOutputResult(): RatedMovieResult? {
    val id = this.id ?: return null
    val title = this.title ?: return null
    val genreIds = this.genreIds?.filterNotNull() ?: emptyList()
    val voteAverage = this.voteAverage?.toFloat() ?: 0f

    val releaseDate = try {
        this.releaseDate?.toLocalDate() ?: return null
    } catch (e: Exception) {
        return null
    }

    val adult = (this.adult == true)
    val backdropPath = this.backdropPath ?: ""
    val originalLanguage = this.originalLanguage ?: ""
    val originalTitle = this.originalTitle ?: ""
    val overview = this.overview ?: ""
    val posterPath = this.posterPath ?: ""
    val video = (this.video == true)
    val poster = this.posterPath ?: ""
    val rating = this.rating ?: 0

    return RatedMovieResult(
        movie = Movie(
            id = id,
            name = title,
            genreIds = genreIds,
            rating = voteAverage,
            releaseDate = releaseDate,
            adult = adult,
            backdropPath = backdropPath,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            posterPath = posterPath,
            video = video,
            poster = poster
        ),
        rating = rating.toFloat()
    )
}
