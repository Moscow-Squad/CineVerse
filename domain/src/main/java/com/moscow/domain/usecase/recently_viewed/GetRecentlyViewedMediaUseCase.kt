package com.moscow.domain.usecase.recently_viewed

import com.moscow.domain.repository.RecentlyViewedRepository
import javax.inject.Inject

class GetRecentlyViewedMediaUseCase @Inject constructor(
    private val repository: RecentlyViewedRepository
) {
    suspend operator fun invoke(): List<Any> {
        return repository.getRecentlyViewedMedia()
    }
}