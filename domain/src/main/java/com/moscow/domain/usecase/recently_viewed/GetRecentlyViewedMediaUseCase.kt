package com.moscow.domain.usecase.recently_viewed

import com.moscow.domain.repository.RecentlyViewedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentlyViewedMediaUseCase @Inject constructor(
    private val repository: RecentlyViewedRepository
) {
    suspend operator fun invoke(): Flow<List<Any>> = repository.getRecentlyViewedMedia()
}