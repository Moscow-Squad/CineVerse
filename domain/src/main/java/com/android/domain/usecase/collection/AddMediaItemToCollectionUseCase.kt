package com.android.domain.usecase.collection

import com.android.domain.model.MediaType
import com.android.domain.repository.CollectionsRepository

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