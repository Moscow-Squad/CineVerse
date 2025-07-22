package com.remote.data_source

import com.data_source.remote.CollectionsDataSource
import com.remote.dto.AddMediaItemToCollectionRequestDto
import com.remote.dto.CreateCollectionDto
import com.remote.services.CollectionsService
import com.utils.handleApi


class CollectionsDataSourceImpl(
    private val collectionsService: CollectionsService
) : CollectionsDataSource {
    override suspend fun getMyCollections(
        accountId: Int,
        sessionId: String
    ) = handleApi {
        collectionsService.getMyCollections(
            accountId,
            sessionId
        )
    }

    override suspend fun addNewCollection(
        collection: CreateCollectionDto,
        sessionId: String
    ) = handleApi {
        collectionsService.addNewCollection(
            collection,
            sessionId
        )
    }

    override suspend fun addMediaItemToCollection(
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

    override suspend fun getCollectionDetails(
        collectionId: Int,
        sessionId: String
    ) = handleApi {
        collectionsService.getCollectionDetails(
            collectionId,
            sessionId
        )
    }
}