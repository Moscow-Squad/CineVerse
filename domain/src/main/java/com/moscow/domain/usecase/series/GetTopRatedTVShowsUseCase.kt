package com.moscow.domain.usecase.series

import com.moscow.domain.model.Series
import com.moscow.domain.repository.SeriesRepository
import javax.inject.Inject

class GetTopRatedTVShowsUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(
        page: Int,
        forceRefresh: Boolean = false
    ): List<Series> =
        seriesRepository.getTopRatedTVSeries(
            page = page,
            forceRefresh = forceRefresh
        )
}