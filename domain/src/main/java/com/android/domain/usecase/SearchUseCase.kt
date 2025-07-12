package com.android.domain.usecase

import com.android.domain.repository.SearchRepository

class SearchUseCase(
    private val searchRepository: SearchRepository,
) {
    suspend fun searchMulti(
        query: String,
    ) = searchRepository.searchMulti(query)

    suspend fun searchMovie(
        query: String,
        isHistory: Boolean = false
    ) = searchRepository.searchMovie(query,isHistory)

    suspend fun searchSeries(
        query: String,
        isHistory: Boolean = false
    ) = searchRepository.searchSeries(query,isHistory)

    suspend fun searchActor(
        query: String,
        isHistory: Boolean = false
    ) = searchRepository.searchActor(query,isHistory)

}