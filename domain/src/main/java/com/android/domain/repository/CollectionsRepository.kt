package com.android.domain.repository

import com.android.domain.model.Collection
import com.android.domain.model.MediaItem
import com.android.domain.model.MediaType
import kotlinx.coroutines.flow.Flow

interface CollectionsRepository {
    suspend fun getCollections(): Flow<List<Collection>>
    suspend fun addNewCollection(
        collectionName: String,
        collectionDescription: String?
    ): Flow<String>

    suspend fun addMediaItemToCollection(
        mediaItemId: Int,
        mediaItemType: MediaType,
        collectionId: Int
    ): Flow<String>

    suspend fun getCollectionDetails(collectionId: Int): Flow<List<MediaItem>>
}