package com.moscow.domain.usecase.series

import com.moscow.domain.model.details.Season
import com.moscow.domain.repository.SeriesRepository

class GetLatestSeasonsUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(): List<Season> = seriesRepository.getLatestSeasons()
}