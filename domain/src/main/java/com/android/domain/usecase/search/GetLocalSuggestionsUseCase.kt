package com.android.domain.usecase.search

import com.android.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetLocalSuggestionsUseCase(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(): Flow<List<String>> =
        searchRepository.getLocalSuggestions()
}