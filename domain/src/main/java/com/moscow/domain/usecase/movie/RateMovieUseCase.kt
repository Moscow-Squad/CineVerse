package com.moscow.domain.usecase.movie

import com.moscow.domain.repository.MovieRepository

class RateMovieUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        rating: Float,
        movieId: Int
    ) = movieRepository.rateMovie(id = movieId, rating = rating)
}