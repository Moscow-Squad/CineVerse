package com.moscow.domain.repository

import com.moscow.domain.model.Collection
import com.moscow.domain.model.MediaItem
import com.moscow.domain.model.MediaType

interface CollectionsRepository {
    suspend fun getCollections(page: Int): List<Collection>
    suspend fun addNewCollection(
        collectionName: String,
        collectionDescription: String?
    ): Int

    suspend fun addMediaItemToCollection(
        mediaItemId: Int,
        mediaItemType: MediaType,
        collectionId: Int
    ): String

    suspend fun getCollectionDetails(collectionId: Int): List<MediaItem>
}