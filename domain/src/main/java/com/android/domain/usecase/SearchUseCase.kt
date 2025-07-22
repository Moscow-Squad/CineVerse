package com.android.domain.usecase

import com.android.domain.repository.SearchRepository

class SearchUseCase(
    private val searchRepository: SearchRepository,
) {
    suspend fun searchMovie(
        query: String,
        page: Int,
        isHistory: Boolean = false
    ) = searchRepository.searchMovie(query, page, isHistory)

    suspend fun searchSeries(
        query: String,
        page: Int,
        isHistory: Boolean = false
    ) = searchRepository.searchSeries(query, page, isHistory)

    suspend fun searchActor(
        query: String,
        page: Int,
        isHistory: Boolean = false
    ) = searchRepository.searchActor(query, page, isHistory)

}