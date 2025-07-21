package com.android.domain.usecase

import com.android.domain.repository.ExploreRepository

class GetMovieByGenreIdUseCase(
    private val exploreRepository: ExploreRepository
) {
    suspend fun getMovieByGenreId(genreId: Int) =
        exploreRepository.getMoviesByGenreId(genreId,1)
}