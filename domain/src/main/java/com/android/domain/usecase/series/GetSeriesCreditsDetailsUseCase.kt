package com.android.domain.usecase.series

import com.android.domain.model.CreditsDetails
import com.android.domain.repository.SeriesRepository

class GetSeriesCreditsDetailsUseCase (private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(id: Int): CreditsDetails {
        return seriesRepository.getSeriesCreditsDetails(id)
    }
}