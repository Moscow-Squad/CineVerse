package com.moscow.domain.usecase.collection

import com.moscow.domain.repository.CollectionsRepository

class GetCollectionDetailsUseCase(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(collectionId: Int) =
        collectionsRepository.getCollectionDetails(collectionId = collectionId)
}