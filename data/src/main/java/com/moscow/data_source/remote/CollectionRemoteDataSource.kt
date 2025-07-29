package com.moscow.data_source.remote

import com.moscow.remote.dto.AddCollectionDto
import com.moscow.remote.dto.AddMediaItemToCollectionRequestDto
import com.moscow.remote.dto.CollectionDto
import com.moscow.remote.dto.CreateCollectionDto
import com.moscow.remote.dto.MediaItemDto
import com.moscow.utils.ApiResponse

interface CollectionRemoteDataSource {
    suspend fun getMyCollections(
        accountId: String,
        sessionId: String,
        page: Int
    ): ApiResponse<CollectionDto>

    suspend fun addNewCollection(
        collection: CreateCollectionDto,
        sessionId: String
    ): AddCollectionDto

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