package com.moscow.domain.usecase.series

import com.moscow.domain.model.details.SeriesDetail
import com.moscow.domain.repository.SeriesRepository

class GetSeriesDetailUseCase(
    private val seriesRepository: SeriesRepository,
) {
    suspend operator fun invoke(id: Int): SeriesDetail = seriesRepository.getSeriesDetail(id)
}