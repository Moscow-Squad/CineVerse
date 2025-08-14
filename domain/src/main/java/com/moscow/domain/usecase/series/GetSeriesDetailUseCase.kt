package com.moscow.domain.usecase.series

import com.moscow.domain.model.Series
import com.moscow.domain.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesDetailUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(
        id: Int
    ): Series = seriesRepository.getSeriesDetail(id = id)
}