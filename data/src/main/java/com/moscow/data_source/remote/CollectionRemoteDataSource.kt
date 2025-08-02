package com.moscow.data_source.remote

import com.moscow.domain.model.MediaType
import com.moscow.remote.dto.AddCollectionDto
import com.moscow.remote.dto.AddMediaItemToCollectionRequestDto
import com.moscow.remote.dto.CollectionDto
import com.moscow.remote.dto.CreateCollectionDto
import com.moscow.remote.dto.MovieDto
import com.moscow.remote.dto.collections_v4.CollectionDetailsV4Dto
import com.moscow.remote.dto.collections_v4.EmptyCollectionApiResponse
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
    ): ApiResponse<Unit>

    suspend fun getCollectionDetails(
        collectionId: Int,
        sessionId: String,
        page: Int
    ): ApiResponse<MovieDto>

    suspend fun getCollectionDetailsV4(
        collectionId: Int,
        page: Int
    ): CollectionDetailsV4Dto

    suspend fun deleteMediaFromCollectionV4(
        collectionId: Int,
        mediaId: Int,
        mediaType: MediaType
    ): EmptyCollectionApiResponse

    suspend fun addMediaFromCollectionV4(
        collectionId: Int,
        mediaId: Int,
        mediaType: MediaType
    ): EmptyCollectionApiResponse
}