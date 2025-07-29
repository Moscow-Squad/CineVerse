package com.moscow.domain.usecase.series

import com.moscow.domain.model.CreditsDetails
import com.moscow.domain.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesCreditsDetailsUseCase @Inject constructor (private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(id: Int): CreditsDetails {
        return seriesRepository.getSeriesCreditsDetails(id)
    }
}