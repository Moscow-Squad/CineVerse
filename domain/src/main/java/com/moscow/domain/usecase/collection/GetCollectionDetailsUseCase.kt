package com.moscow.domain.usecase.collection

import com.moscow.domain.repository.CollectionsRepository
import javax.inject.Inject

class GetCollectionDetailsUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(collectionId: Int) =
        collectionsRepository.getCollectionDetails(collectionId = collectionId)
}