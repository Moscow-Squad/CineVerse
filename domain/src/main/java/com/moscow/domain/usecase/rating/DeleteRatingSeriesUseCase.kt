package com.moscow.domain.usecase.rating

import com.moscow.domain.repository.SeriesRepository
import javax.inject.Inject

class DeleteRatingSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(id: Int) = seriesRepository.deleteRatingSeries(id)
}