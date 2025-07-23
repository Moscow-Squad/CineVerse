package com.repository

import com.android.domain.model.Collection
import com.android.domain.model.MediaItem
import com.android.domain.model.MediaType
import com.android.domain.repository.CollectionsRepository
import com.data_source.remote.CollectionsDataSource
import com.mapper.toDomain
import com.remote.dto.AddMediaItemToCollectionRequestDto
import com.remote.dto.CreateCollectionDto
import com.remote.dto.toDomain

class CollectionsRepositoryImpl(
    private val collectionsDataSource: CollectionsDataSource
) : CollectionsRepository {
    override suspend fun getCollections(page: Int): List<Collection> {
        val response = collectionsDataSource.getMyCollections(
            accountId = 22117857,
            sessionId = "31044f799b3ccf5e970b994ca0022ef8865c1e35",
            page = 1
        )
        return response.results?.map { it.toDomain() } ?: emptyList()
    }


    override suspend fun addNewCollection(
        collectionName: String,
        collectionDescription: String?
    ): String {
        val collection =
            CreateCollectionDto(name = collectionName, description = collectionDescription)
        val response = collectionsDataSource.addNewCollection(
            collection = collection,
            sessionId = "31044f799b3ccf5e970b994ca0022ef8865c1e35"
        )
        return response.statusMessage ?: "Unexpected Error happened"
    }


    override suspend fun addMediaItemToCollection(
        mediaItemId: Int,
        mediaItemType: MediaType,
        collectionId: Int
    ): String {
        val item =
            AddMediaItemToCollectionRequestDto(
                mediaId = mediaItemId,
                mediaType = mediaItemType.name
            )
        val response = collectionsDataSource.addMediaItemToCollection(
            item = item,
            collectionId = collectionId,
            sessionId = "31044f799b3ccf5e970b994ca0022ef8865c1e35"
        )
        return response.statusMessage ?: "Unexpected Error happened"
    }


    override suspend fun getCollectionDetails(collectionId: Int): List<MediaItem> {
        val response = collectionsDataSource.getCollectionDetails(
            collectionId = collectionId,
            sessionId = "31044f799b3ccf5e970b994ca0022ef8865c1e35"
        )
        return response.results?.map { it.toDomain() } ?: emptyList()
    }
}

