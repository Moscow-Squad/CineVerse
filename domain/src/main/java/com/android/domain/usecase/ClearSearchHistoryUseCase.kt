package com.android.domain.usecase

import com.android.domain.repository.SearchRepository

class ClearSearchHistoryUseCase(
    private val searchRepository: SearchRepository
) {
    suspend fun clearSearchHistory() = searchRepository.clearSearchHistory()
}