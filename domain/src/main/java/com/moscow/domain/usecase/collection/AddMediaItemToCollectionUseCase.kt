package com.moscow.domain.usecase.collection

import com.moscow.domain.model.MediaType
import com.moscow.domain.repository.CollectionsRepository
import javax.inject.Inject

class AddMediaItemToCollectionUseCase @Inject constructor(
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