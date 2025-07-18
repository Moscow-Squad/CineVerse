package com.android.domain.usecase

import com.android.domain.repository.SearchRepository

class CacheSearchQueryUseCase(
    private val searchRepository: SearchRepository
) {
    suspend fun cacheSearchQuery(
        query: String
    ) = searchRepository.cacheSearchQuery(query)

}