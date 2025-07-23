package com.android.domain.usecase.series

import com.android.domain.repository.SeriesRepository

class GetSeriesByGenreIdUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(genreId: Int) = seriesRepository.getSeriesByGenreId(genreId, 1)
}