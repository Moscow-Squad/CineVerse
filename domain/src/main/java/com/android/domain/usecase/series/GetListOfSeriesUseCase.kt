package com.android.domain.usecase.series

import com.android.domain.model.details.ListOfSeries
import com.android.domain.repository.SeriesRepository

class GetListOfSeriesUseCase(
    private val seriesRepository: SeriesRepository,
) {
    suspend operator fun invoke(id: Int, page: Int): List<ListOfSeries> =
        seriesRepository.getListOfSeries(id, page)
}