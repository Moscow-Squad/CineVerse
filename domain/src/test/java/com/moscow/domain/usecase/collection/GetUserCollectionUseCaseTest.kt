package com.moscow.domain.usecase.collection

import com.google.common.truth.Truth.assertThat
import com.moscow.domain.repository.CollectionsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetUserCollectionsUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var getUserCollectionsUseCase: GetUserCollectionsUseCase

    @BeforeEach
    fun setUp() {
        collectionsRepository = mockk(relaxed = true)
        getUserCollectionsUseCase = GetUserCollectionsUseCase(collectionsRepository)
    }

    @Test
    fun `getUserCollectionsUseCase should call repository method`() = runTest {
        // Given
        val page = 1

        // When
        getUserCollectionsUseCase(page)

        // Then
        coVerify(exactly = 1) { collectionsRepository.getCollections(page) }
    }

    @Test
    fun `getUserCollectionsUseCase should return result from repository`() = runTest {
        // Given
        val page = 3
        coEvery { collectionsRepository.getCollections(any()) } returns emptyList()

        // When
        val result = getUserCollectionsUseCase(page)

        // Then
        assertThat(result).isNotNull()
        coVerify(exactly = 1) { collectionsRepository.getCollections(page) }
    }
    
    @Test
    fun `getUserCollectionsUseCase should handle multiple invocations`() = runTest {
        // Given
        coEvery { collectionsRepository.getCollections(any()) } returns emptyList()

        // When
        repeat(3) { i -> getUserCollectionsUseCase(i + 1) }

        // Then
        coVerify(exactly = 3) { collectionsRepository.getCollections(any()) }
    }

    @Test
    fun `getUserCollectionsUseCase makes exactly one repository call`() = runTest {
        // Given
        val page = 5

        // When
        getUserCollectionsUseCase(page)

        // Then
        coVerify(exactly = 1) { collectionsRepository.getCollections(page) }
    }
}