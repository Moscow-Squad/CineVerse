package com.android.domain.usecase.series

import com.android.domain.repository.SeriesRepository

class GetSeriesRecommendationsUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(id: Int, page: Int) =
        seriesRepository.getSeriesRecommendations(id, page)
}