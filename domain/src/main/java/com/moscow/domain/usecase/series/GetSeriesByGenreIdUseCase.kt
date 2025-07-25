package com.moscow.domain.usecase.series

import com.moscow.domain.repository.SeriesRepository

class GetSeriesByGenreIdUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(genreId: Int, page: Int) = seriesRepository.getSeriesByGenreId(genreId, page)
}