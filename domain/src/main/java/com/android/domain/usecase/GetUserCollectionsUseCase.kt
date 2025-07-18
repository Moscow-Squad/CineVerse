package com.android.domain.usecase

import com.android.domain.repository.CollectionsRepository

class GetUserCollectionsUseCase(
    private val repository: CollectionsRepository
) {
    suspend fun getUseCollections() = repository.getCollections()
}