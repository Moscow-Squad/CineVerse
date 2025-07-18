package com.android.domain.usecase

import com.android.domain.repository.DetailsRepository

class RateSeriesUseCase(
    private val repository: DetailsRepository
) {
    suspend fun rateMovieUse(
        rating: Float,
        seriesId: Int
    ) =
        repository.rateSeries(rating = rating, seriesId = seriesId)
}