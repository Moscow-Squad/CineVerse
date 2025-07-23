package com.android.domain.usecase

import com.android.domain.repository.CollectionsRepository

class GetUserCollectionsUseCase(
    private val repository: CollectionsRepository
) {
    suspend operator fun invoke(page: Int) = repository.getCollections(page)
}