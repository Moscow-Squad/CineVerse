package com.moscow.domain.usecase.search

import com.moscow.domain.repository.SearchRepository
import javax.inject.Inject

class ClearSearchHistoryUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke() = searchRepository.clearSearchHistory()
}