package com.android.domain.usecase.movie

import com.android.domain.repository.MovieRepository

class RateMovieUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        rating: Float,
        movieId: Int
    ) = movieRepository.rateMovie(id = movieId, rating = rating)
}