package com.android.domain.usecase

import com.android.domain.repository.RecommendationsMoviesRepository

class GetRecommendationsUseCase(
    private val recommendationsMoviesRepository: RecommendationsMoviesRepository
) {
    suspend operator fun invoke(id:Int,page:Int) =
        recommendationsMoviesRepository.getRecommendationsMovies(id,page)
}