package com.android.domain.usecase

import com.android.domain.exception.CineVerseException.NoSuggestionFoundException
import com.android.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class GetLocalSuggestions(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(): Flow<List<String>> {
        val suggestions = movieRepository.getLocalSuggestions()
        return if (suggestions.first().isEmpty()) throw NoSuggestionFoundException() else suggestions
    }
}