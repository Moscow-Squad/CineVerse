package com.android.domain.usecase

import com.android.domain.repository.DetailsRepository

class RateMovieUseCase(
    private val repository: DetailsRepository
) {
    suspend fun rateMovie(
        rating: Float,
        movieId: Int
    ) =
        repository.rateMovie(rating = rating, movieId = movieId)
}