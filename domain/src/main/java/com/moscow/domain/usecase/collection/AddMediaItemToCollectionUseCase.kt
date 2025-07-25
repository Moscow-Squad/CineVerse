package com.moscow.domain.usecase.collection

import com.moscow.domain.model.MediaType
import com.moscow.domain.repository.CollectionsRepository

class AddMediaItemToCollectionUseCase(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(
        mediaItemId: Int,
        mediaItemType: MediaType,
        collectionId: Int
    ) =
        collectionsRepository.addMediaItemToCollection(
            mediaItemId = mediaItemId,
            mediaItemType = mediaItemType,
            collectionId = collectionId
        )
}