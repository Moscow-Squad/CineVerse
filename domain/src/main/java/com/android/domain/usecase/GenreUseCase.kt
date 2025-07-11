package com.android.domain.usecase

import com.android.domain.repository.ExploreRepository

class GenreUseCase(
    private val exploreRepository: ExploreRepository
) {
    suspend fun getMoviesGenres() =
        exploreRepository.getMoviesGenres()

    suspend fun getSeriesGenres() =
        exploreRepository.getSeriesGenres()
}