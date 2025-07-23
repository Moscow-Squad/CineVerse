package com.android.domain.usecase.search

import com.android.domain.repository.SearchRepository

class SuggestionUseCase(
    private val searchRepository: SearchRepository,
) {
    suspend fun getSuggestions(keyWord: String, page: Int): List<String> =
        searchRepository.getRemoteSuggestions(keyWord, page)
}