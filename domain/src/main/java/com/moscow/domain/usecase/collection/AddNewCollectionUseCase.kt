package com.moscow.domain.usecase.collection

import com.moscow.domain.repository.CollectionsRepository
import javax.inject.Inject

class AddNewCollectionUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(
        collectionName: String,
        collectionDescription: String?
    ) =
        collectionsRepository.addNewCollection(
            collectionName = collectionName,
            collectionDescription = collectionDescription
        )
}