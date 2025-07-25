package com.moscow.domain.usecase.series

import com.moscow.domain.repository.SeriesRepository

class RateSeriesUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend fun rateSeriesUse(
        rating: Float,
        seriesId: Int
    ) = seriesRepository.rateSeries(id = seriesId, rating = rating)
}