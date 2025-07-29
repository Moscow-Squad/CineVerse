package com.moscow.remote.services

import com.moscow.remote.dto.AddMediaItemToCollectionRequestDto
import com.moscow.remote.dto.CollectionDto
import com.moscow.remote.dto.CreateCollectionDto
import com.moscow.remote.dto.MediaItemDto
import com.moscow.utils.ACCOUNT
import com.moscow.utils.ADD_ITEM
import com.moscow.utils.ApiResponse
import com.moscow.utils.LIST
import com.moscow.utils.LISTS
import com.moscow.utils.PAGE
import com.moscow.utils.SESSION_ID
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CollectionsService {
    @GET("$ACCOUNT/{account_id}$LISTS")
    suspend fun getMyCollections(
        @Path("account_id") accountId: String,
        @Query(PAGE) page: Int,
        @Query(SESSION_ID) sessionId: String
    ): Response<ApiResponse<CollectionDto>>

    @POST(LIST)
    suspend fun addNewCollection(
        @Body collection: CreateCollectionDto,
        @Query(SESSION_ID) sessionId: String
    ): Response<ApiResponse<Nothing>>

    @POST("$LIST/{collection_id}/$ADD_ITEM")
    suspend fun addMediaItemToCollection(
        @Body item: AddMediaItemToCollectionRequestDto,
        @Path("collection_id") collectionId: Int,
        @Query(SESSION_ID) sessionId: String
    ): Response<ApiResponse<Nothing>>

    @GET("$LIST/{collection_id}")
    suspend fun getCollectionDetails(
        @Path("collection_id") collectionId: Int,
        @Query(SESSION_ID) sessionId: String
    ): Response<ApiResponse<MediaItemDto>>
}
