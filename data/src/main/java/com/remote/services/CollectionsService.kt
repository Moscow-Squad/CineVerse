package com.remote.services

import com.remote.dto.AddMediaItemToCollectionRequestDto
import com.remote.dto.CollectionDto
import com.remote.dto.CreateCollectionDto
import com.remote.dto.MediaItemDto
import com.utils.ACCOUNT
import com.utils.ADD_ITEM
import com.utils.ApiResponse
import com.utils.LIST
import com.utils.LISTS
import com.utils.PAGE
import com.utils.SESSION_ID
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CollectionsService {
    @GET("$ACCOUNT{accountId}$LISTS")
    suspend fun getMyCollections(
        @Path("accountId") accountId: Int,
        @Query(PAGE) page: Int,
        @Query(SESSION_ID) sessionId: String
    ): Response<ApiResponse<CollectionDto>>

    @POST(LIST)
    suspend fun addNewCollection(
        @Body collection: CreateCollectionDto,
        @Query(SESSION_ID) sessionId: String
    ): Response<ApiResponse<Nothing>>

    @POST("$LIST/{collectionId}/$ADD_ITEM")
    suspend fun addMediaItemToCollection(
        @Body item: AddMediaItemToCollectionRequestDto,
        @Path("collectionId") collectionId: Int,
        @Query(SESSION_ID) sessionId: String
    ): Response<ApiResponse<Nothing>>

    @GET("$LIST/{collectionId}")
    suspend fun getCollectionDetails(
        @Path("collectionId") collectionId: Int,
        @Query(SESSION_ID) sessionId: String
    ): Response<ApiResponse<MediaItemDto>>
}