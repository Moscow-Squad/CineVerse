package com.moscow.domain.usecase.series

import com.moscow.domain.model.CreditsInfo
import com.moscow.domain.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesCreditsDetailsUseCase @Inject constructor (
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(
        id: Int
    ): CreditsInfo = seriesRepository.getSeriesCreditsDetails(id = id)
}