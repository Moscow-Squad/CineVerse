package com.android.domain.usecase

import com.android.domain.exception.CineVerseException.NoSuggestionFoundException
import com.android.domain.repository.MovieRepository

class GetLocalSuggestions(private val movieRepository: MovieRepository) {
    operator fun invoke(): List<String> {
        val suggestions = movieRepository.getLocalSuggestions()
        return if (suggestions.isEmpty()) throw NoSuggestionFoundException() else suggestions
    }
}