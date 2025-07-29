package com.moscow.domain.usecase.series

import com.moscow.domain.repository.SeriesRepository
import javax.inject.Inject

class GetPopularSeriesUseCase @Inject constructor(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(page: Int) = seriesRepository.getPopularSeries(page = page)
}