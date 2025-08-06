package com.moscow.domain.usecase.home

import com.moscow.domain.repository.HomeRepository
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(time: String? = null, forceRefresh: Boolean = false) =
        homeRepository.getTrendingMovies(time, forceRefresh)
}