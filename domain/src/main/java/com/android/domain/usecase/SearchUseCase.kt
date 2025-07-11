package com.android.domain.usecase

import com.android.domain.repository.ExploreRepository

class SearchUseCase(
    private val exploreRepository: ExploreRepository
) {
    suspend fun searchMulti(
        query: String,
    ) = exploreRepository.searchMulti(query)

    suspend fun searchMovie(
        query: String,
    ) = exploreRepository.searchMovie(query)

    suspend fun searchSeries(
        query: String
    ) = exploreRepository.searchSeries(query)

    suspend fun searchActor(
        query: String
    ) = exploreRepository.searchActor(query)
}