package com.android.domain.usecase

import com.android.domain.repository.ExploreRepository

class GetSeriesByGenreIdUseCase(
    private val exploreRepository: ExploreRepository
) {
    suspend fun getSeriesByGenreId(genreId: Int) = exploreRepository.getSeriesByGenreId(genreId, 1)
}