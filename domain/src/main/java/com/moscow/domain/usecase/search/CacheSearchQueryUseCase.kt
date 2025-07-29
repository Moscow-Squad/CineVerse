package com.moscow.domain.usecase.search

import com.moscow.domain.repository.SearchRepository
import javax.inject.Inject

class CacheSearchQueryUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend fun cacheSearchQuery(
        query: String
    ) = searchRepository.cacheSearchQuery(query)

}