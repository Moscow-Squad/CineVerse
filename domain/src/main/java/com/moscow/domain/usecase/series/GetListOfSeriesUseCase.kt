package com.moscow.domain.usecase.series

import com.moscow.domain.model.details.ListOfSeries
import com.moscow.domain.repository.SeriesRepository
import javax.inject.Inject

class GetListOfSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository,
) {
    suspend operator fun invoke(id: Int, page: Int): List<ListOfSeries> =
        seriesRepository.getListOfSeries(id, page)
}