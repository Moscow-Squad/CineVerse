package com.android.domain.usecase

import com.android.domain.MovieRepository

class GetMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke() = repository.geMovies()
}

