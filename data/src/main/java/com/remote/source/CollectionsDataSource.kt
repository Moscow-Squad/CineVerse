package com.remote.source

import com.remote.dto.AddMediaItemToCollectionRequestDto
import com.remote.dto.CollectionDto
import com.remote.dto.CreateCollectionDto
import com.remote.dto.MediaItemDto
import com.utils.ACCOUNT
import com.utils.ADD_ITEM
import com.utils.ApiResponse
import com.utils.LIST
import com.utils.LISTS
import com.utils.SESSION_ID
import com.utils.performCall
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.http.HttpMethod

class CollectionsDataSource(
    private val httpClient: HttpClient,
) {
    suspend fun getMyCollections(accountId: Int, sessionId: String): List<CollectionDto> =
        httpClient.performCall<Unit, ApiResponse<CollectionDto>>(
            method = HttpMethod.Get,
            path = "$ACCOUNT${accountId}$LISTS",
            requestBuilder = {
                parameter(SESSION_ID, sessionId)
            }
        ).results

    suspend fun addNewCollection(collection: CreateCollectionDto, sessionId: String) =
        httpClient.performCall<CreateCollectionDto, ApiResponse<Unit>>(
            method = HttpMethod.Post,
            path = LIST,
            requestBuilder = {
                parameter(SESSION_ID, sessionId)
            },
            body = collection
        )

    suspend fun addMediaItemToCollection(
        item: AddMediaItemToCollectionRequestDto,
        collectionId: Int,
        sessionId: String
    ) =
        httpClient.performCall<AddMediaItemToCollectionRequestDto, ApiResponse<Unit>>(
            method = HttpMethod.Post,
            path = "$LIST/${collectionId}/$ADD_ITEM",
            requestBuilder = {
                parameter(SESSION_ID, sessionId)
            },
            body = item
        )

    suspend fun getCollectionDetails(collectionId: Int, sessionId: String) =
        httpClient.performCall<Unit, ApiResponse<MediaItemDto>>(
            method = HttpMethod.Get,
            path = "$LIST/${collectionId}",
            requestBuilder = {
                parameter(SESSION_ID, sessionId)
            }
        ).results
}