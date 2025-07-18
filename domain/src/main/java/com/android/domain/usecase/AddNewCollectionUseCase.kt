package com.android.domain.usecase

import com.android.domain.repository.CollectionsRepository

class AddNewCollectionUseCase(
    private val collectionsRepository: CollectionsRepository
) {
    suspend fun addNewCollection(
        collectionName: String,
        collectionDescription: String?
    ) =
        collectionsRepository.addNewCollection(
            collectionName = collectionName,
            collectionDescription = collectionDescription
        )
}