package com.moscow.domain.usecase.search

import com.moscow.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetLocalSuggestionsUseCase(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(): Flow<List<String>> =
        searchRepository.getLocalSuggestions()
}