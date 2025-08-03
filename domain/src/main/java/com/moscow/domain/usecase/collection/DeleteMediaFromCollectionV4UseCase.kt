package com.moscow.domain.usecase.collection

import com.moscow.domain.model.MediaType
import com.moscow.domain.repository.CollectionsRepository

class DeleteMediaFromCollectionV4UseCase constructor(
    private val collectionsRepository: CollectionsRepository
){
    suspend operator fun invoke(
        collectionId: Int,
        mediaId: Int,
        mediaType: MediaType
    ) = collectionsRepository.deleteMediaFromCollectionV4(collectionId, mediaId, mediaType)
}