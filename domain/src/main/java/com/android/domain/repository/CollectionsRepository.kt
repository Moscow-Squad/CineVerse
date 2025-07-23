package com.android.domain.repository

import com.android.domain.model.Collection
import com.android.domain.model.MediaItem
import com.android.domain.model.MediaType

interface CollectionsRepository {
    suspend fun getCollections(page: Int): List<Collection>
    suspend fun addNewCollection(
        collectionName: String,
        collectionDescription: String?
    ): String

    suspend fun addMediaItemToCollection(
        mediaItemId: Int,
        mediaItemType: MediaType,
        collectionId: Int
    ): String

    suspend fun getCollectionDetails(collectionId: Int): List<MediaItem>
}