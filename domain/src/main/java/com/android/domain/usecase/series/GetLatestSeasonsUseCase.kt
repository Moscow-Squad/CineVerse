package com.android.domain.usecase.series

import com.android.domain.model.details.Season
import com.android.domain.repository.SeriesRepository

class GetLatestSeasonsUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(): List<Season> = seriesRepository.getLatestSeasons()
}