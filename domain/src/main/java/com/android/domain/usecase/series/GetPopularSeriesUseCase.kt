package com.android.domain.usecase.series

import com.android.domain.repository.SeriesRepository

class GetPopularSeriesUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(page: Int) = seriesRepository.getPopularSeries(page = page)
}