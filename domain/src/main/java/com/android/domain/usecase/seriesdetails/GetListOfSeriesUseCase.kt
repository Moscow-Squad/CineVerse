package com.android.domain.usecase.seriesdetails

import com.android.domain.model.details.ListOfSeries
import com.android.domain.repository.DetailsRepository

class GetListOfSeriesUseCase(
    private val detailsRepository: DetailsRepository,
) {
    suspend operator fun invoke(id: Int, page: Int): List<ListOfSeries> =
        detailsRepository.getListOfSeries(id, page)
}