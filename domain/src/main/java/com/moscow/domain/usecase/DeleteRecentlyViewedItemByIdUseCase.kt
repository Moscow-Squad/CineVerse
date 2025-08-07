package com.moscow.domain.usecase

import com.moscow.domain.repository.RecentlyViewedRepository
import javax.inject.Inject

class DeleteRecentlyViewedItemByIdUseCase @Inject constructor(
    private val repository: RecentlyViewedRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.deleteRecentlyViewedItemById(id)
    }
}