package com.moscow.domain.usecase.series

import com.moscow.domain.model.details.SeriesDetail
import com.moscow.domain.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesDetailUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository,
) {
    suspend operator fun invoke(id: Int): SeriesDetail = seriesRepository.getSeriesDetail(id)
}