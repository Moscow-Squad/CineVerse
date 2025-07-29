package com.moscow.domain.usecase.series

import com.moscow.domain.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesRecommendationsUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(id: Int, page: Int) =
        seriesRepository.getSeriesRecommendations(id, page)
}