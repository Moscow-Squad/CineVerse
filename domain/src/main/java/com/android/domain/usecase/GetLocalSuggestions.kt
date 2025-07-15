package com.android.domain.usecase

import com.android.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetLocalSuggestions(private val searchRepository: SearchRepository) {
    suspend fun localSuggestions(): Flow<List<String>> =
        searchRepository.getLocalSuggestions()
}