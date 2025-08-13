package com.moscow.domain.usecase.collection

import com.google.common.truth.Truth.assertThat
import com.moscow.domain.repository.CollectionsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteMediaItemFromCollectionUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var deleteMediaItemFromCollectionUseCase: DeleteMediaItemFromCollectionUseCase

    @BeforeEach
    fun setUp() {
        collectionsRepository = mockk(relaxed = true)
        deleteMediaItemFromCollectionUseCase =
            DeleteMediaItemFromCollectionUseCase(collectionsRepository)
    }

    @Test
    fun `deleteMediaItemFromCollectionUseCase should call repository method`() = runTest {
        // Given
        val mediaItemId = 10
        val collectionId = 5

        // When
        deleteMediaItemFromCollectionUseCase(mediaItemId, collectionId)

        // Then
        coVerify(exactly = 1) {
            collectionsRepository.deleteMovieFromCollection(mediaItemId, collectionId)
        }
    }

    @Test
    fun `deleteMediaItemFromCollectionUseCase should return result from repository`() = runTest {
        // Given
        val mediaItemId = 11
        val collectionId = 7
        coEvery {
            collectionsRepository.deleteMovieFromCollection(any(), any())
        } returns Unit

        // When
        val result = deleteMediaItemFromCollectionUseCase(mediaItemId, collectionId)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `deleteMediaItemFromCollectionUseCase should complete operation successfully`() = runTest {
        // Given
        val mediaItemId = 12
        val collectionId = 8

        // When
        val result = deleteMediaItemFromCollectionUseCase(mediaItemId, collectionId)

        // Then
        assertThat(result).isNotNull()
        coVerify(exactly = 1) {
            collectionsRepository.deleteMovieFromCollection(mediaItemId, collectionId)
        }
    }

    @Test
    fun `deleteMediaItemFromCollectionUseCase should handle multiple invocations`() = runTest {
        // Given
        val collectionId = 9

        // When
        repeat(3) { i ->
            deleteMediaItemFromCollectionUseCase(mediaItemId = 100 + i, collectionId = collectionId)
        }

        // Then
        coVerify(exactly = 3) {
            collectionsRepository.deleteMovieFromCollection(any(), collectionId)
        }
    }

    @Test
    fun `deleteMediaItemFromCollectionUseCase makes exactly one repository call`() = runTest {
        // Given
        val mediaItemId = 13
        val collectionId = 6

        // When
        deleteMediaItemFromCollectionUseCase(mediaItemId, collectionId)

        // Then
        coVerify(exactly = 1) {
            collectionsRepository.deleteMovieFromCollection(mediaItemId, collectionId)
        }
    }
}
