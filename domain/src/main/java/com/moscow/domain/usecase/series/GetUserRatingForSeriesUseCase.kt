package com.moscow.domain.usecase.series

import com.moscow.domain.repository.SeriesRepository
import javax.inject.Inject

class GetUserRatingForSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
){
    suspend operator fun invoke(seriesId : Int) = seriesRepository.getUserRatingForSeries(seriesId)
}