package com.moscow.domain.usecase.search

import com.moscow.domain.repository.SearchRepository

class ClearSearchHistoryUseCase(
    private val searchRepository: SearchRepository
) {
    suspend fun clearSearchHistory() = searchRepository.clearSearchHistory()
}