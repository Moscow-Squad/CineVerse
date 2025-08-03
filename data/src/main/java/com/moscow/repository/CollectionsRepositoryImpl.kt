package com.moscow.repository

import com.moscow.data_source.remote.CollectionRemoteDataSource
import com.moscow.domain.repository.CollectionsRepository
import com.moscow.mapper.toDomain
import com.moscow.remote.dto.AddMediaItemToCollectionRequestDto
import com.moscow.remote.dto.CreateCollectionDto
import com.moscow.remote.dto.toDomain
import com.moscow.domain.model.Collection
import com.moscow.domain.model.MediaType
import com.moscow.domain.model.Movie
import com.moscow.domain.model.UserType
import com.moscow.domain.repository.PreferenceRepository
import com.moscow.remote.dto.collections_v4.toDomain
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
            is UserType.GuestUser -> "0"
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
        mediaItemType: MediaType,
        collectionId: Int
    ): String {
        val item = AddMediaItemToCollectionRequestDto(mediaId = mediaItemId)
        val response = collectionRemoteDataSource.addMediaItemToCollection(
            item = item,
            collectionId = collectionId,
            sessionId = preferenceRepository.getSessionId()
        )
        return response.statusMessage ?: "Unexpected Error happened"
    }

    override suspend fun getCollectionDetails(collectionId: Int, page: Int): List<Movie> {
        val response = collectionRemoteDataSource.getCollectionDetails(
            collectionId = collectionId,
            sessionId = preferenceRepository.getSessionId(),
            page = page
        )
        return response.items?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getCollectionDetailsV4(collectionId: Int, page: Int) =
        collectionRemoteDataSource.getCollectionDetailsV4(collectionId, page).toDomain()

    override suspend fun deleteMediaFromCollectionV4(
        collectionId: Int,
        mediaId: Int,
        mediaType: MediaType
    ) {
        collectionRemoteDataSource.deleteMediaFromCollectionV4(collectionId, mediaId, mediaType)
    }

    override suspend fun addMediaToCollectionV4(
        collectionId: Int,
        mediaId: Int,
        mediaType: MediaType
    ) {
        collectionRemoteDataSource.addMediaToCollectionV4(collectionId, mediaId, mediaType)
    }
}
