package com.android.domain.usecase.seriesdetails

import com.android.domain.model.details.SeriesDetail
import com.android.domain.repository.DetailsRepository

class GetSeriesDetailUseCase(
    private val repository: DetailsRepository,
) {
    suspend operator fun invoke(id: Int): SeriesDetail {
        return repository.getSeriesDetail(id)
    }
}