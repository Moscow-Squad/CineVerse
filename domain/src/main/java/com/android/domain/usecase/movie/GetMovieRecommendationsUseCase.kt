package com.android.domain.usecase.movie

import com.android.domain.repository.MovieRepository

class GetMovieRecommendationsUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(id:Int,page:Int) =
        movieRepository.getMovieRecommendations(id,page)
}