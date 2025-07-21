package com.remote.services

import retrofit2.http.*
import com.remote.dto.*
import com.remote.dto.MediaItemDto
import com.utils.ACCOUNT
import com.utils.ADD_ITEM
import com.utils.ApiResponse
import com.utils.LIST
import com.utils.LISTS
import com.utils.SESSION_ID
import retrofit2.Response

interface CollectionsService {

    @GET("$ACCOUNT{accountId}$LISTS")
    suspend fun getMyCollections(
        @Path("accountId") accountId: Int,
        @Query(SESSION_ID) sessionId: String
    ): Response<ApiResponse<CollectionDto>>

    @POST(LIST)
    suspend fun addNewCollection(
        @Body collection: CreateCollectionDto,
        @Query(SESSION_ID) sessionId: String
    ): Response<ApiResponse<Unit>>

    @POST("$LIST/{collectionId}/$ADD_ITEM")
    suspend fun addMediaItemToCollection(
        @Body item: AddMediaItemToCollectionRequestDto,
        @Path("collectionId") collectionId: Int,
        @Query(SESSION_ID) sessionId: String
    ): Response<ApiResponse<Unit>>

    @GET("$LIST/{collectionId}")
    suspend fun getCollectionDetails(
        @Path("collectionId") collectionId: Int,
        @Query(SESSION_ID) sessionId: String
    ): Response<ApiResponse<MediaItemDto>>
}