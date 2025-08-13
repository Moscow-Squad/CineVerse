package com.moscow.domain.usecase.rating

import com.moscow.domain.repository.MovieRepository
import javax.inject.Inject

class RateMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        rating: Float,
        movieId: Int
    ) = movieRepository.addRatingMovie(
        id = movieId,
        rating = rating
    )
}