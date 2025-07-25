package com.moscow.domain.usecase.movie

import com.moscow.domain.repository.MovieRepository

class GetMovieRecommendationsUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(id:Int,page:Int) =
        movieRepository.getMovieRecommendations(id,page)
}