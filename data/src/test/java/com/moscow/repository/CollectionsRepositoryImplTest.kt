package com.moscow.repository

import com.moscow.data_source.remote.CollectionRemoteDataSource
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
import org.junit.jupiter.api.Assertions.assertEquals

class CollectionsRepositoryImplTest {

    private val collectionRemoteDataSource = mockk<CollectionRemoteDataSource>()
    private val userRepository = mockk<UserRepository>()
    private lateinit var repository: CollectionsRepositoryImpl

    @BeforeEach
    fun setup() {
        repository = CollectionsRepositoryImpl(collectionRemoteDataSource, userRepository)
    }

    @Test
    fun `addNewCollection calls remote data source correctly`() = runTest {
        // Arrange
        val collectionName = "New Collection"
        val collectionDescription = "Test Description"
        val sessionId = "test-session-id"
        val expectedListId = 123

        val mockAddCollectionDto = AddCollectionDto(
            listId = expectedListId,
            statusCode = 123,
            statusMessage = "Success",
            success = true
        )

        coEvery { userRepository.getSessionId() } returns sessionId
        coEvery {
            collectionRemoteDataSource.addNewCollection(
                collection = CreateCollectionRequestDto(
                    name = collectionName,
                    description = collectionDescription
                ),
                sessionId = sessionId
            )
        } returns mockAddCollectionDto

        try {
            repository.addNewCollection(collectionName, collectionDescription)
            assertEquals(true, true)
        } catch (e: Exception) {
            assertEquals("Expected successful method call", e.message ?: "Unexpected error")
        }

        // Verify the data source methods were called
        coVerify(exactly = 1) { userRepository.getSessionId() }
        coVerify(exactly = 1) {
            collectionRemoteDataSource.addNewCollection(
                collection = CreateCollectionRequestDto(
                    name = collectionName,
                    description = collectionDescription
                ),
                sessionId = sessionId
            )
        }
    }

    @Test
    fun `addMovieToCollection calls remote data source correctly`() = runTest {
        // Arrange
        val mediaItemId = 456
        val collectionId = 123
        val sessionId = "test-session-id"

        val mockApiResponse = ApiResponse<Unit>(
            success = true
        )

        coEvery { userRepository.getSessionId() } returns sessionId
        coEvery {
            collectionRemoteDataSource.addMediaItemToCollection(
                item = AddMediaItemToCollectionRequestDto(mediaId = mediaItemId),
                collectionId = collectionId,
                sessionId = sessionId
            )
        } returns mockApiResponse

        try {
            repository.addMovieToCollection(mediaItemId, collectionId)
            assertEquals(true, true)
        } catch (e: Exception) {
            assertEquals("Expected successful method call", e.message ?: "Unexpected error")
        }

        coVerify(exactly = 1) { userRepository.getSessionId() }
        coVerify(exactly = 1) {
            collectionRemoteDataSource.addMediaItemToCollection(
                item = AddMediaItemToCollectionRequestDto(mediaId = mediaItemId),
                collectionId = collectionId,
                sessionId = sessionId
            )
        }
    }

    @Test
    fun `deleteMovieFromCollection calls remote data source correctly`() = runTest {

        val mediaItemId = 456
        val collectionId = 123
        val sessionId = "test-session-id"

        val mockApiResponse = ApiResponse<Unit>(
            success = true
        )

        coEvery { userRepository.getSessionId() } returns sessionId
        coEvery {
            collectionRemoteDataSource.deleteMediaItemFromCollection(
                item = AddMediaItemToCollectionRequestDto(mediaId = mediaItemId),
                collectionId = collectionId,
                sessionId = sessionId
            )
        } returns mockApiResponse

        try {
            repository.deleteMovieFromCollection(mediaItemId, collectionId)
            assertEquals(true, true)
        } catch (e: Exception) {
            assertEquals("Expected successful method call", e.message ?: "Unexpected error")
        }

        coVerify(exactly = 1) { userRepository.getSessionId() }
        coVerify(exactly = 1) {
            collectionRemoteDataSource.deleteMediaItemFromCollection(
                item = AddMediaItemToCollectionRequestDto(mediaId = mediaItemId),
                collectionId = collectionId,
                sessionId = sessionId
            )
        }
    }

    @Test
    fun `clearCollection calls remote data source correctly`() = runTest {

        val collectionId = 123
        val sessionId = "test-session-id"

        val mockApiResponse = ApiResponse<Unit>(
            success = true
        )

        coEvery { userRepository.getSessionId() } returns sessionId
        coEvery {
            collectionRemoteDataSource.clearCollection(
                collectionId = collectionId,
                sessionId = sessionId
            )
        } returns mockApiResponse

        try {
            repository.clearCollection(collectionId)
            assertEquals(true, true)
        } catch (e: Exception) {
            assertEquals("Expected successful method call", e.message ?: "Unexpected error")
        }

        coVerify(exactly = 1) { userRepository.getSessionId() }
        coVerify(exactly = 1) {
            collectionRemoteDataSource.clearCollection(
                collectionId = collectionId,
                sessionId = sessionId
            )
        }
    }
}