package com.android.domain.usecase.collection

import com.android.domain.repository.CollectionsRepository

class AddNewCollectionUseCase(
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