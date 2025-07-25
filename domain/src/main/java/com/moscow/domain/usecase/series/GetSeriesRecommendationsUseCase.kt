package com.moscow.domain.usecase.series

import com.moscow.domain.repository.SeriesRepository

class GetSeriesRecommendationsUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(id: Int, page: Int) =
        seriesRepository.getSeriesRecommendations(id, page)
}