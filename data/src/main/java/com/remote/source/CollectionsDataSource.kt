package com.remote.source

import com.remote.dto.CollectionDto
import com.utils.ACCOUNT
import com.utils.ApiResponse
import com.utils.LIST
import com.utils.performCall
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

class CollectionsDataSource(
    private val httpClient: HttpClient,
) {

    suspend fun getMyCollections(accountId: Int?): List<CollectionDto> =
        httpClient.performCall<Unit, ApiResponse<CollectionDto>>(
            method = HttpMethod.Get,
            path = "$ACCOUNT${accountId}$LIST"
        ).results

    suspend fun addNewCollection(collection: CollectionDto) {

    }

    suspend fun addMediaItemToCollection(mediaId: Int, collectionId: Int) {

    }

    suspend fun getCollectionDetails(collectionId: Int) {

    }
}