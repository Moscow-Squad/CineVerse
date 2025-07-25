package com.android.domain.usecase.home

import com.moscow.domain.repository.HomeRepository
import com.moscow.domain.model.Series

class GetTopRatedTVShowsUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(page:Int): List<Series> =
        homeRepository.getTopRatedTVSeries(page)
}