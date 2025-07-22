package com.android.domain.usecase

import com.android.domain.repository.ExploreRepository

class GetSeriesUseCase(private val repository: ExploreRepository) {
    suspend operator fun invoke() = repository.getSeries(1)
}
