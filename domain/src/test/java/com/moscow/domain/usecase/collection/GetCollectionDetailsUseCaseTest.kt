package com.moscow.domain.usecase.collection

import com.google.common.truth.Truth.assertThat
import com.moscow.domain.repository.CollectionsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetCollectionDetailsUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var getCollectionDetailsUseCase: GetCollectionDetailsUseCase

    @BeforeEach
    fun setUp() {
        collectionsRepository = mockk(relaxed = true)
        getCollectionDetailsUseCase = GetCollectionDetailsUseCase(collectionsRepository)
    }

    @Test
    fun `getCollectionDetailsUseCase should call repository method`() = runTest {
        // Given
        val collectionId = 42
        val page = 1

        // When
        getCollectionDetailsUseCase(collectionId, page)

        // Then
        coVerify(exactly = 1) {
            collectionsRepository.getCollectionMovies(
                collectionId = collectionId,
                page = page
            )
        }
    }

    @Test
    fun `getCollectionDetailsUseCase should return result from repository`() = runTest {
        // Given
        val collectionId = 7
        val page = 3
        coEvery {
            collectionsRepository.getCollectionMovies(any(), any())
        } returns emptyList()

        // When
        val result = getCollectionDetailsUseCase(collectionId, page)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `getCollectionDetailsUseCase should complete operation successfully`() = runTest {
        // Given
        val collectionId = 9
        val page = 2

        // When
        val result = getCollectionDetailsUseCase(collectionId, page)

        // Then
        assertThat(result).isNotNull()
        coVerify(exactly = 1) {
            collectionsRepository.getCollectionMovies(
                collectionId = collectionId,
                page = page
            )
        }
    }

    @Test
    fun `getCollectionDetailsUseCase should handle multiple invocations`() = runTest {
        // Given
        val collectionId = 10

        // When
        repeat(3) { i ->
            getCollectionDetailsUseCase(collectionId, page = i + 1)
        }

        // Then
        coVerify(exactly = 3) {
            collectionsRepository.getCollectionMovies(collectionId = collectionId, page = any())
        }
    }

    @Test
    fun `getCollectionDetailsUseCase makes exactly one repository call`() = runTest {
        // Given
        val collectionId = 100
        val page = 5

        // When
        getCollectionDetailsUseCase(collectionId, page)

        // Then
        coVerify(exactly = 1) {
            collectionsRepository.getCollectionMovies(
                collectionId = collectionId,
                page = page
            )
        }
    }
}
