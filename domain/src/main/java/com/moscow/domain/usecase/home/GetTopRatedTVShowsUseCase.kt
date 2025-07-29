package com.moscow.domain.usecase.home

import com.moscow.domain.repository.HomeRepository
import com.moscow.domain.model.Series
import javax.inject.Inject

class GetTopRatedTVShowsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(page:Int): List<Series> =
        homeRepository.getTopRatedTVSeries(page)
}