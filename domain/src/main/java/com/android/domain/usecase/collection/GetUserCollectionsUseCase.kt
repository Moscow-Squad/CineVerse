package com.android.domain.usecase.collection

import com.android.domain.repository.CollectionsRepository

class GetUserCollectionsUseCase(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(page: Int) = collectionsRepository.getCollections(page)
}