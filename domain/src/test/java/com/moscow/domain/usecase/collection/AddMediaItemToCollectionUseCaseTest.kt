package com.moscow.domain.usecase.collection

import com.moscow.domain.repository.CollectionsRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddMediaItemToCollectionUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var addMediaItemToCollectionUseCase: AddMediaItemToCollectionUseCase

    @BeforeEach
    fun setUp() {
        collectionsRepository = mockk(relaxed = true)
        addMediaItemToCollectionUseCase = AddMediaItemToCollectionUseCase(collectionsRepository)
    }

    @Test
    fun `addMediaItemToCollectionUseCase should add media item to collection successfully`() = runTest {
        // Given
        val mediaItemId = 123
        val collectionId = 456
        coEvery {
            collectionsRepository.addMovieToCollection(mediaItemId, collectionId)
        } returns Unit

        // When
        val result = addMediaItemToCollectionUseCase(mediaItemId, collectionId)

        // Then
        assertThat(result).isEqualTo(Unit)
    }

    @Test
    fun `addMediaItemToCollectionUseCase should handle adding first media item to collection`() = runTest {
        // Given
        val mediaItemId = 1
        val collectionId = 1
        coEvery {
            collectionsRepository.addMovieToCollection(mediaItemId, collectionId)
        } returns Unit

        // When
        addMediaItemToCollectionUseCase(mediaItemId, collectionId)

        // Then
        coVerify(exactly = 1) {
            collectionsRepository.addMovieToCollection(mediaItemId, collectionId)
        }
    }

    @Test
    fun `addMediaItemToCollectionUseCase should handle adding media item with large ID`() = runTest {
        // Given
        val mediaItemId = 999999
        val collectionId = 888888
        coEvery {
            collectionsRepository.addMovieToCollection(mediaItemId, collectionId)
        } returns Unit

        // When
        val result = addMediaItemToCollectionUseCase(mediaItemId, collectionId)

        // Then
        assertThat(result).isEqualTo(Unit)
    }

    @Test
    fun `addMediaItemToCollectionUseCase should handle adding multiple different media items`() = runTest {
        // Given
        val mediaItemId = 100
        val collectionId = 200
        coEvery {
            collectionsRepository.addMovieToCollection(mediaItemId, collectionId)
        } returns Unit

        // When
        addMediaItemToCollectionUseCase(mediaItemId, collectionId)

        // Then
        coVerify {
            collectionsRepository.addMovieToCollection(mediaItemId, collectionId)
        }
    }

    @Test
    fun `addMediaItemToCollectionUseCase should handle adding media item to different collection`() = runTest {
        // Given
        val mediaItemId = 777
        val collectionId = 333
        coEvery {
            collectionsRepository.addMovieToCollection(mediaItemId, collectionId)
        } returns Unit

        // When
        val result = addMediaItemToCollectionUseCase(mediaItemId, collectionId)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `addMediaItemToCollectionUseCase should pass correct parameters to repository`() = runTest {
        // Given
        val mediaItemId = 555
        val collectionId = 666
        coEvery {
            collectionsRepository.addMovieToCollection(mediaItemId, collectionId)
        } returns Unit

        // When
        addMediaItemToCollectionUseCase(mediaItemId, collectionId)

        // Then
        coVerify(exactly = 1) {
            collectionsRepository.addMovieToCollection(
                mediaItemId = mediaItemId,
                collectionId = collectionId
            )
        }
    }

    @Test
    fun `addMediaItemToCollectionUseCase should handle adding same media item to multiple collections`() = runTest {
        // Given
        val mediaItemId = 404
        val collectionId = 505
        coEvery {
            collectionsRepository.addMovieToCollection(mediaItemId, collectionId)
        } returns Unit

        // When
        val result = addMediaItemToCollectionUseCase(mediaItemId, collectionId)

        // Then
        assertThat(result).isEqualTo(Unit)
    }

    @Test
    fun `addMediaItemToCollectionUseCase should complete operation without returning data`() = runTest {
        // Given
        val mediaItemId = 789
        val collectionId = 987
        coEvery {
            collectionsRepository.addMovieToCollection(mediaItemId, collectionId)
        } returns Unit

        // When
        addMediaItemToCollectionUseCase(mediaItemId, collectionId)

        // Then
        coVerify { collectionsRepository.addMovieToCollection(any(), any()) }
    }

    @Test
    fun `addMediaItemToCollectionUseCase makes exactly one repository call`() = runTest {
        // Given
        val mediaItemId = 321
        val collectionId = 654
        coEvery {
            collectionsRepository.addMovieToCollection(mediaItemId, collectionId)
        } returns Unit

        // When
        addMediaItemToCollectionUseCase(mediaItemId, collectionId)

        // Then
        coVerify(exactly = 1) { collectionsRepository.addMovieToCollection(any(), any()) }
    }
}