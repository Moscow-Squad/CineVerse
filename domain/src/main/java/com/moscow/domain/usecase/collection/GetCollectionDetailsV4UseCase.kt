package com.moscow.domain.usecase.collection

import com.moscow.domain.repository.CollectionsRepository
import javax.inject.Inject

class GetCollectionDetailsV4UseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
){
    suspend operator fun invoke(
        collectionId: Int,
        page: Int
    ) = collectionsRepository.getCollectionDetailsV4(collectionId, page)
}