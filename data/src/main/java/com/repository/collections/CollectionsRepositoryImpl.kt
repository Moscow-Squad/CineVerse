package com.repository.collections

import com.android.domain.model.Collection
import com.android.domain.model.MediaItem
import com.android.domain.model.MediaType
import com.android.domain.repository.CollectionsRepository
import com.mapper.toDomain
import com.remote.dto.AddMediaItemToCollectionRequestDto
import com.remote.dto.CreateCollectionDto
import com.remote.dto.toDomain
import com.remote.source.CollectionsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CollectionsRepositoryImpl(
    private val collectionsDataSource: CollectionsDataSource
) : CollectionsRepository {
    override suspend fun getCollections(): Flow<List<Collection>> =

        flow {
            val response = collectionsDataSource.getMyCollections(
                accountId = 22117857,
                sessionId = "31044f799b3ccf5e970b994ca0022ef8865c1e35"
            )
            emit(response.results.map { it.toDomain() })
        }.flowOn(Dispatchers.IO)


    override suspend fun addNewCollection(
        collectionName: String,
        collectionDescription: String?
    ): Flow<String> =

        flow {
            val collection =
                CreateCollectionDto(name = collectionName, description = collectionDescription)
            val response = collectionsDataSource.addNewCollection(
                collection = collection,
                sessionId = "31044f799b3ccf5e970b994ca0022ef8865c1e35"
            )
            response.statusMessage?.let { emit(it) }
        }.flowOn(Dispatchers.IO)

    override suspend fun addMediaItemToCollection(
        mediaItemId: Int,
        mediaItemType: MediaType,
        collectionId: Int
    ): Flow<String> =


        flow {
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
            response.statusMessage?.let { emit(it) }
        }.flowOn(Dispatchers.IO)


    override suspend fun getCollectionDetails(collectionId: Int): Flow<List<MediaItem>> =

        flow {
            val response = collectionsDataSource.getCollectionDetails(
                collectionId = collectionId,
                sessionId = "31044f799b3ccf5e970b994ca0022ef8865c1e35"
            )
            emit(response.results.map { it.toDomain() })
        }.flowOn(Dispatchers.IO)

}