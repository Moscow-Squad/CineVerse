package com.moscow.remote.data_source

import com.moscow.data_source.remote.CollectionRemoteDataSource
import com.moscow.remote.dto.AddMediaItemToCollectionRequestDto
import com.moscow.remote.dto.CollectionDto
import com.moscow.remote.dto.CreateCollectionDto
import com.moscow.remote.dto.movie.MovieDto
import com.moscow.remote.services.CollectionsService
import com.moscow.utils.ApiResponse
import com.moscow.utils.handleApi
import javax.inject.Inject

class CollectionRemoteDataSourceImpl  @Inject constructor(
    private val collectionsService: CollectionsService
) : CollectionRemoteDataSource {
    override suspend fun getMyCollections(
        accountId: String,
        sessionId: String,
        page: Int
    ): ApiResponse<CollectionDto> = handleApi {
        collectionsService.getMyCollections(
            accountId,
            page,
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
    ): ApiResponse<Unit> = handleApi {
        collectionsService.addMediaItemToCollection(
            item,
            collectionId,
            sessionId
        )
    }

    override suspend fun getCollectionDetails(
        collectionId: Int,
        sessionId: String,
        page:Int
    ): ApiResponse<MovieDto> = handleApi {
        collectionsService.getCollectionDetails(
            collectionId,
            sessionId,
            page
        )
    }

    override suspend fun deleteMediaItemFromCollection(
        item: AddMediaItemToCollectionRequestDto,
        collectionId: Int,
        sessionId: String
    ) = handleApi {
        collectionsService.deleteMediaItemFromCollection(
            item,
            collectionId,
            sessionId
        )
    }

    override suspend fun clearCollection(
        collectionId: Int,
        sessionId: String
    ) = handleApi {
        collectionsService.clearCollection(
            collectionId,
            sessionId
        )
    }
}