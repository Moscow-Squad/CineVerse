package com.android.domain.usecase.series

import com.android.domain.model.details.SeriesDetail
import com.android.domain.repository.SeriesRepository

class GetSeriesDetailUseCase(
    private val seriesRepository: SeriesRepository,
) {
    suspend operator fun invoke(id: Int): SeriesDetail = seriesRepository.getSeriesDetail(id)
}