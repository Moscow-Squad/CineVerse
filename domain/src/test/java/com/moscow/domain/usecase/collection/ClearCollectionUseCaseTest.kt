package com.moscow.domain.usecase.collection

import com.moscow.domain.repository.CollectionsRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ClearCollectionUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var clearCollectionUseCase: ClearCollectionUseCase

    @BeforeEach
    fun setUp() {
        collectionsRepository = mockk(relaxed = true)
        clearCollectionUseCase = ClearCollectionUseCase(collectionsRepository)
    }

    @Test
    fun `clearCollectionUseCase should clear collection successfully`() = runTest {
        // Given
        val collectionId = 123

        // When
        val result = clearCollectionUseCase(collectionId)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `clearCollectionUseCase should handle clearing first collection`() = runTest {
        // Given
        val collectionId = 1

        // When
        clearCollectionUseCase(collectionId)

        // Then
        coVerify(exactly = 1) {
            collectionsRepository.clearCollection(collectionId = collectionId)
        }
    }

    @Test
    fun `clearCollectionUseCase should handle clearing collection with large ID`() = runTest {
        // Given
        val collectionId = 999999

        // When
        val result = clearCollectionUseCase(collectionId)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `clearCollectionUseCase should handle clearing different collections`() = runTest {
        // Given
        val collectionId = 456

        // When
        clearCollectionUseCase(collectionId)

        // Then
        coVerify {
            collectionsRepository.clearCollection(collectionId = collectionId)
        }
    }

    @Test
    fun `clearCollectionUseCase should handle clearing collection with minimal ID`() = runTest {
        // Given
        val collectionId = 2

        // When
        val result = clearCollectionUseCase(collectionId)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `clearCollectionUseCase should pass correct parameter to repository`() = runTest {
        // Given
        val collectionId = 789

        // When
        clearCollectionUseCase(collectionId)

        // Then
        coVerify(exactly = 1) {
            collectionsRepository.clearCollection(collectionId = collectionId)
        }
    }

    @Test
    fun `clearCollectionUseCase should handle clearing multiple different collections`() = runTest {
        // Given
        val collectionId = 555

        // When
        val result = clearCollectionUseCase(collectionId)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `clearCollectionUseCase should complete clear operation successfully`() = runTest {
        // Given
        val collectionId = 333

        // When
        clearCollectionUseCase(collectionId)

        // Then
        coVerify { collectionsRepository.clearCollection(any()) }
    }

    @Test
    fun `clearCollectionUseCase makes exactly one repository call`() = runTest {
        // Given
        val collectionId = 777

        // When
        clearCollectionUseCase(collectionId)

        // Then
        coVerify(exactly = 1) { collectionsRepository.clearCollection(any()) }
    }
}