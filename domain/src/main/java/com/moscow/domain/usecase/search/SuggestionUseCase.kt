package com.moscow.domain.usecase.search

import com.moscow.domain.repository.SearchRepository
import javax.inject.Inject


class SuggestionUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
) {
    suspend fun invoke(
        keyWord: String,
        page: Int
    ): List<String> = searchRepository.getRemoteSuggestions(keyWord, page)
}