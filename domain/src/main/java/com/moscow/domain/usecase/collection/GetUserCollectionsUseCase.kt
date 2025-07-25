package com.moscow.domain.usecase.collection

import com.moscow.domain.repository.CollectionsRepository

class GetUserCollectionsUseCase(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(page: Int) = collectionsRepository.getCollections(page)
}