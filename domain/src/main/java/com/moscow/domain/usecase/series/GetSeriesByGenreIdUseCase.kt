package com.moscow.domain.usecase.series

import com.moscow.domain.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesByGenreIdUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(
        genreId: Int,
        page: Int
    ) = seriesRepository.getSeriesByGenreId(genreId, page).distinctBy { it.id }
}