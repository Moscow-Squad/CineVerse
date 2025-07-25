package com.android.domain.usecase.home

import com.moscow.domain.repository.HomeRepository

class GetTrendingMoviesUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(time: String? = null) =
        homeRepository.getTrendingMovies(time)
}