package com.android.domain.usecase.seriesdetails

import com.android.domain.model.CreditsDetails
import com.android.domain.repository.DetailsRepository

class GetSeriesCreditsDetailsUseCase (private val repository: DetailsRepository) {
    suspend operator fun invoke(id: Int): CreditsDetails {
        return repository.getSeriesCreditsDetails(id)
    }
}