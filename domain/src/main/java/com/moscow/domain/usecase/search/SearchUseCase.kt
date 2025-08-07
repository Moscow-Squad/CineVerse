package com.moscow.domain.usecase.search

import com.moscow.domain.repository.SearchRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
) {
    suspend fun searchMovie(
        query: String,
        page: Int
    ) = searchRepository.searchMovie(query, page)

    suspend fun searchSeries(
        query: String,
        page: Int
    ) = searchRepository.searchSeries(query, page)

    suspend fun searchActor(
        query: String,
        page: Int
    ) = searchRepository.searchActor(query, page)
}