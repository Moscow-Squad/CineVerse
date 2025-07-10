package com.android.domain.usecase

import com.android.domain.MovieRepository

class GetSeriesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke() = repository.getSeries()
}
