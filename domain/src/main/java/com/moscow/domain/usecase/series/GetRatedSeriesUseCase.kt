package com.moscow.domain.usecase.series

import com.moscow.domain.model.Series
import com.moscow.domain.repository.SeriesRepository
import jakarta.inject.Inject

class GetRatedSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {

    suspend operator fun invoke(userId: Int, page: Int) =
        seriesRepository.getRatedSeries(userId, page)

    data class RatedSeriesResult(
        val series: Series,
        val rating: Float
    )
}