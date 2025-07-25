package com.moscow.domain.usecase.series

import com.moscow.domain.repository.SeriesRepository

class GetPopularSeriesUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(page: Int) = seriesRepository.getPopularSeries(page = page)
}