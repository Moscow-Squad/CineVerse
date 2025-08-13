package com.moscow.domain.usecase.rating

import com.moscow.domain.repository.SeriesRepository
import javax.inject.Inject

class RateSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend fun rateSeriesUse(
        rating: Float,
        seriesId: Int
    ) = seriesRepository.rateSeries(id = seriesId, rating = rating)
}