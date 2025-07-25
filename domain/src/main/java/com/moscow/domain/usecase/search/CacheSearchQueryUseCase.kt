package com.moscow.domain.usecase.search

import com.moscow.domain.repository.SearchRepository

class CacheSearchQueryUseCase(
    private val searchRepository: SearchRepository
) {
    suspend fun cacheSearchQuery(
        query: String
    ) = searchRepository.cacheSearchQuery(query)

}