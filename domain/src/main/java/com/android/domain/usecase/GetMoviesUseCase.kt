package com.android.domain.usecase

import com.android.domain.repository.ExploreRepository

class GetMoviesUseCase(private val repository: ExploreRepository) {
    suspend operator fun invoke() = repository.geMovies()
}

