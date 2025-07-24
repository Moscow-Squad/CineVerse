package com.data_source.remote

import com.remote.dto.AddMediaItemToCollectionRequestDto
import com.remote.dto.CollectionDto
import com.remote.dto.CreateCollectionDto
import com.remote.dto.MediaItemDto
import com.utils.ApiResponse

interface CollectionRemoteDataSource {
    suspend fun getMyCollections(
        accountId: Int,
        sessionId: String,
        page: Int
    ): ApiResponse<CollectionDto>

    suspend fun addNewCollection(
        collection: CreateCollectionDto,
        sessionId: String
    ): ApiResponse<Nothing>

    suspend fun addMediaItemToCollection(
        item: AddMediaItemToCollectionRequestDto,
        collectionId: Int,
        sessionId: String
    ): ApiResponse<Nothing>

    suspend fun getCollectionDetails(
        collectionId: Int,
        sessionId: String
    ): ApiResponse<MediaItemDto>
}