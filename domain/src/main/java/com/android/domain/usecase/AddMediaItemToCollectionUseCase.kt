package com.android.domain.usecase

import com.android.domain.model.MediaType
import com.android.domain.repository.CollectionsRepository

class AddMediaItemToCollectionUseCase(
    private val collectionsRepository: CollectionsRepository
) {
    suspend fun addMediaItemToCollection(
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