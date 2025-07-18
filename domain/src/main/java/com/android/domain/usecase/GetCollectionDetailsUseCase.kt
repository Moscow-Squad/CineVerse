package com.android.domain.usecase

import com.android.domain.repository.CollectionsRepository

class GetCollectionDetailsUseCase(
    private val collectionsRepository: CollectionsRepository
) {
    suspend fun getCollectionDetails(collectionId: Int) =
        collectionsRepository.getCollectionDetails(collectionId = collectionId)
}