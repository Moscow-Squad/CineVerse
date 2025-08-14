package com.moscow.domain.repository

import com.moscow.domain.model.Collection
import com.moscow.domain.model.Movie

interface CollectionsRepository {
    suspend fun getCollections(page: Int): List<Collection>
    suspend fun addNewCollection(
        collectionName: String,
        collectionDescription: String?
    ): Int
    suspend fun addMovieToCollection(
        mediaItemId: Int,
        collectionId: Int
    )
    suspend fun deleteMovieFromCollection(
        mediaItemId: Int,
        collectionId: Int
    )
    suspend fun getCollectionMovies(
        collectionId: Int,
        page: Int
    ): List<Movie>
    suspend fun clearCollection(collectionId: Int)
}