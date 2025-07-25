package com.android.domain.usecase.home

import com.android.domain.model.Series
import com.android.domain.repository.HomeRepository

class GetTopRatedTVShowsUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(page:Int): List<Series> =
        homeRepository.getTopRatedTVSeries(page)
}