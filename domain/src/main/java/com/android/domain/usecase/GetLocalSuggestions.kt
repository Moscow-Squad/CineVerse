package com.android.domain.usecase

import com.android.domain.exception.CineVerseException.NoSuggestionFoundException
import com.android.domain.repository.SearchRepository

class GetLocalSuggestions(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(): List<String> {
        val suggestions = searchRepository.getLocalSuggestions()
        return if (suggestions.isEmpty()) throw NoSuggestionFoundException() else suggestions
    }
}