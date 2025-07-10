package com.android.domain.usecase

import com.android.domain.repository.MovieRepository

class GetLocalSuggestions( val movieRepository: MovieRepository ) {
    operator fun invoke() : List<String> {
        return emptyList()
    }
}