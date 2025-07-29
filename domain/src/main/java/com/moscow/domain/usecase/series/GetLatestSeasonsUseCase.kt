package com.moscow.domain.usecase.series

import com.moscow.domain.model.details.Season
import com.moscow.domain.repository.SeriesRepository
import javax.inject.Inject

class GetLatestSeasonsUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(): List<Season> = seriesRepository.getLatestSeasons()
}