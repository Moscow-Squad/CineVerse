package com.remote.source

import com.remote.dto.AddMediaItemToCollectionRequestDto
import com.remote.dto.CreateCollectionDto
import com.remote.services.CollectionsService
import com.utils.handleApi


class CollectionsDataSource(
    private val collectionsService: CollectionsService
) {
    suspend fun getMyCollections(
        accountId: Int,
        sessionId: String
    ) = handleApi {
        collectionsService.getMyCollections(
            accountId,
            sessionId
        )
    }

    suspend fun addNewCollection(
        collection: CreateCollectionDto,
        sessionId: String
    ) = handleApi {
        collectionsService.addNewCollection(
            collection,
            sessionId
        )
    }

    suspend fun addMediaItemToCollection(
        item: AddMediaItemToCollectionRequestDto,
        collectionId: Int,
        sessionId: String
    ) = handleApi {
        collectionsService.addMediaItemToCollection(
            item,
            collectionId,
            sessionId
            )
    }

    suspend fun getCollectionDetails(
        collectionId: Int,
        sessionId: String
    ) = handleApi {
        collectionsService.getCollectionDetails(
            collectionId ,
            sessionId
        )
    }
}