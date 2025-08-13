package com.moscow.domain.usecase.rating

import com.moscow.domain.repository.MovieRepository
import javax.inject.Inject

class GetUserRatingForMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId : Int) = movieRepository.getUserRatingForMovie(movieId)
}