package com.android.domain.usecase.collection

import com.android.domain.repository.CollectionsRepository

class GetCollectionDetailsUseCase(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(collectionId: Int) =
        collectionsRepository.getCollectionDetails(collectionId = collectionId)
}