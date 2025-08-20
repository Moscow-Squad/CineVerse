package com.moscow.repository

import com.google.common.truth.Truth.assertThat
import com.moscow.data_source.remote.CollectionRemoteDataSource
import com.moscow.domain.exception.CineVerseException.*
import com.moscow.domain.model.UserType
import com.moscow.domain.repository.auth.UserRepository
import com.moscow.remote.dto.collection.request.AddMediaItemToCollectionRequestDto
import com.moscow.remote.dto.collection.request.CreateCollectionRequestDto
import com.moscow.remote.dto.collection.response.AddCollectionDto
import com.moscow.remote.dto.collection.response.CollectionDto
import com.moscow.remote.dto.media_item.movie.MovieDto
import com.moscow.utils.ApiResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CollectionsRepositoryImplTest {

    private val collectionRemoteDataSource = mockk<CollectionRemoteDataSource>()
    private val userRepository = mockk<UserRepository>()
    private lateinit var repository: CollectionsRepositoryImpl

    @BeforeEach
    fun setup() {
        repository = CollectionsRepositoryImpl(collectionRemoteDataSource, userRepository)
    }

    @Test
    fun `addNewCollection returns listId on success`() = runTest {
        val sessionId = "test-session-id"
        val expectedListId = 123
        val name = "New Collection"
        val description = "Test Description"

        coEvery { userRepository.getSessionId() } returns sessionId
        coEvery {
            collectionRemoteDataSource.addNewCollection(
                CreateCollectionRequestDto(name, description),
                sessionId
            )
        } returns AddCollectionDto(
            listId = expectedListId,
            success = true,
            statusMessage = null,
            statusCode = null
        )

        val result = repository.addNewCollection(name, description)

        assertThat(result).isEqualTo(expectedListId)
        coVerify { collectionRemoteDataSource.addNewCollection(any(), sessionId) }
    }

    @Test
    fun `addMovieToCollection throws exception when success is false`() = runTest {
        val sessionId = "test-session-id"
        val mediaItemId = 456
        val collectionId = 123

        coEvery { userRepository.getSessionId() } returns sessionId
        coEvery {
            collectionRemoteDataSource.addMediaItemToCollection(
                AddMediaItemToCollectionRequestDto(mediaId = mediaItemId),
                collectionId,
                sessionId
            )
        } returns ApiResponse(success = false)

        assertThrows<AddMediaItemToCollectionException> {
            repository.addMovieToCollection(mediaItemId, collectionId)
        }
    }

    @Test
    fun `deleteMovieFromCollection throws exception when success is false`() = runTest {
        val sessionId = "test-session-id"
        val mediaItemId = 456
        val collectionId = 123

        coEvery { userRepository.getSessionId() } returns sessionId
        coEvery {
            collectionRemoteDataSource.deleteMediaItemFromCollection(
                AddMediaItemToCollectionRequestDto(mediaId = mediaItemId),
                collectionId,
                sessionId
            )
        } returns ApiResponse(success = false)

        assertThrows<DeleteMediaItemFromCollectionException> {
            repository.deleteMovieFromCollection(mediaItemId, collectionId)
        }
    }

    @Test
    fun `clearCollection throws exception when success is false`() = runTest {
        val sessionId = "test-session-id"
        val collectionId = 123

        coEvery { userRepository.getSessionId() } returns sessionId
        coEvery {
            collectionRemoteDataSource.clearCollection(collectionId, sessionId)
        } returns ApiResponse(success = false)

        assertThrows<ClearCollectionException> {
            repository.clearCollection(collectionId)
        }
    }

    @Test
    fun `getCollections returns mapped collections for authenticated user`() = runTest {
        val sessionId = "test-session-id"
        val user = UserType.AuthenticatedUser(
            id = "user123",
            username = "Ahmed",
            name = "Ahmed M.",
            sessionId = sessionId,
            image = null,
            recentlyCollectionId = 0
        )

        val remoteDto = CollectionDto(name = "My Collection")
        coEvery { userRepository.getUser() } returns user
        coEvery { userRepository.getSessionId() } returns sessionId
        coEvery {
            collectionRemoteDataSource.getMyCollections("user123", sessionId, 1)
        } returns ApiResponse(results = listOf(remoteDto))

        val result = repository.getCollections(1)

        assertThat(result).hasSize(1)
        assertThat(result.first().name).isEqualTo("My Collection")
    }

    @Test
    fun `getCollections throws NotAllowedUserException for guest user`() = runTest {
        val user = UserType.GuestUser(sessionId = "guest", expiredAt = "2025-12-31")
        coEvery { userRepository.getUser() } returns user

        assertThrows<NotAllowedUserException> {
            repository.getCollections(1)
        }
    }
}