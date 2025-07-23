package com.android.domain.usecase.seriesdetails

import com.android.domain.repository.RecommendationsMoviesRepository

class GetSeriesRecommendationsUseCase(
    private val recommendationsMoviesRepository: RecommendationsMoviesRepository
) {
    suspend operator fun invoke(id:Int,page:Int) =
        recommendationsMoviesRepository.getSeriesRecommendations(id,page)
}