package com.moscow.domain.usecase

import com.moscow.domain.repository.RecentlyViewedRepository

class DeleteRecentlyViewedItemByIdUseCase(
    private val repository: RecentlyViewedRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.deleteRecentlyViewedItemById(id)
    }
}