package com.moscow.domain.usecase.movie

import com.moscow.domain.model.Movie
import com.moscow.domain.repository.MovieRepository
import javax.inject.Inject

class GetRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(userId: Int, page: Int) =
        movieRepository.getRatedMovies(userId, page)

    data class RatedMovieResult(
        val movie: Movie,
        val rating: Float
    )
}