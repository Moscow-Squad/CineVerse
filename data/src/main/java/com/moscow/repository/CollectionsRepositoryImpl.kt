package com.moscow.repository

import com.moscow.data_source.remote.CollectionRemoteDataSource
import com.moscow.domain.model.Collection
import com.moscow.domain.model.Movie
import com.moscow.domain.model.UserType
import com.moscow.domain.repository.CollectionsRepository
import com.moscow.domain.repository.PreferenceRepository
import com.moscow.mapper.toDomain
import com.moscow.remote.dto.AddMediaItemToCollectionRequestDto
import com.moscow.remote.dto.CreateCollectionDto
import com.moscow.remote.dto.toDomain
import com.moscow.utils.CineVerseExceptions
import javax.inject.Inject

class CollectionsRepositoryImpl @Inject constructor(
    private val collectionRemoteDataSource: CollectionRemoteDataSource,
    private val preferenceRepository: PreferenceRepository
) : CollectionsRepository {
    override suspend fun getCollections(page: Int): List<Collection> {
        val user = preferenceRepository.getUser()
        val accountId = when (user) {
            is UserType.AuthenticatedUser -> user.id
            is UserType.GuestUser -> throw IllegalArgumentException("Guest Users not allowed to see or edit collections")
        }
        val response = collectionRemoteDataSource.getMyCollections(
            accountId = accountId,
            sessionId = preferenceRepository.getSessionId(),
            page = page
        )
        return response.results?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun addNewCollection(
        collectionName: String,
        collectionDescription: String?
    ): Int {
        val collection =
            CreateCollectionDto(name = collectionName, description = collectionDescription)
        val response = collectionRemoteDataSource.addNewCollection(
            collection = collection,
            sessionId = preferenceRepository.getSessionId()
        )
        return response.listId ?: throw CineVerseExceptions(
            response.statusCode ?: 0,
            response.statusMessage ?: ""
        )
    }

    override suspend fun addMediaItemToCollection(
        mediaItemId: Int,
        collectionId: Int
    ): String {
        val item = AddMediaItemToCollectionRequestDto(mediaId = mediaItemId)
        val response = collectionRemoteDataSource.addMediaItemToCollection(
            item = item,
            collectionId = collectionId,
            sessionId = preferenceRepository.getSessionId()
        )
        if (response.success == false)
            throw CineVerseExceptions(
                response.statusCode ?: 0,
                response.statusMessage ?: ""
            )
        return response.statusMessage.toString()
    }

    override suspend fun deleteMediaItemFromCollection(
        mediaItemId: Int,
        collectionId: Int
    ): String {
        val item = AddMediaItemToCollectionRequestDto(mediaId = mediaItemId)
        val response = collectionRemoteDataSource.deleteMediaItemFromCollection(
            item = item,
            collectionId = collectionId,
            sessionId = preferenceRepository.getSessionId()
        )
        if (response.success == false)
            throw CineVerseExceptions(
                response.statusCode ?: 0,
                response.statusMessage ?: ""
            )
        return response.statusMessage.toString()
    }

    override suspend fun getCollectionDetails(collectionId: Int, page: Int): List<Movie> {
        val response = collectionRemoteDataSource.getCollectionDetails(
            collectionId = collectionId,
            sessionId = preferenceRepository.getSessionId(),
            page = page
        )
        return response.items?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun clearCollection(
        collectionId: Int,
        confirm: Boolean
    ): String {
        val response = collectionRemoteDataSource.clearCollection(
            collectionId = collectionId,
            sessionId = preferenceRepository.getSessionId(),
            confirm = confirm
        )
        if (response.success == false)
            throw CineVerseExceptions(
                response.statusCode ?: 0,
                response.statusMessage ?: ""
            )
        return response.statusMessage.toString()
    }
}
