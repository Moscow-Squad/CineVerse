package com.moscow.domain.usecase.series

import com.moscow.domain.model.CreditsDetails
import com.moscow.domain.repository.SeriesRepository

class GetSeriesCreditsDetailsUseCase (private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(id: Int): CreditsDetails {
        return seriesRepository.getSeriesCreditsDetails(id)
    }
}