package com.moscow.domain.usecase.collection

import com.moscow.domain.model.MediaType
import com.moscow.domain.repository.CollectionsRepository
import javax.inject.Inject

class AddMediaToCollectionV4UseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
){
    suspend operator fun invoke(
        collectionId: Int,
        mediaId: Int,
        mediaType: MediaType
    ) = collectionsRepository.addMediaToCollectionV4(collectionId, mediaId, mediaType)
}