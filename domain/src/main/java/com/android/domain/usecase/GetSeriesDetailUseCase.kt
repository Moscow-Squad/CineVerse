package com.android.domain.usecase

import com.android.domain.model.SeriesDetail
import com.android.domain.repository.DetailsRepository

class GetSeriesDetailUseCase(
    private val repository: DetailsRepository,
) {
    suspend operator fun invoke(id: Int): SeriesDetail {
        return repository.getSeriesDetail(id)
    }
}