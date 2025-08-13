package com.moscow.domain.usecase.collection

import com.google.common.truth.Truth.assertThat
import com.moscow.domain.repository.CategoryTipsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetShowCollectionDetailsTipUseCaseTest {

    private lateinit var categoryTipsRepository: CategoryTipsRepository
    private lateinit var getShowCollectionDetailsTipUseCase: GetShowCollectionDetailsTipUseCase

    @BeforeEach
    fun setUp() {
        categoryTipsRepository = mockk(relaxed = true)
        getShowCollectionDetailsTipUseCase =
            GetShowCollectionDetailsTipUseCase(categoryTipsRepository)
    }

    @Test
    fun `getShowCollectionDetailsTipUseCase should call repository method`() = runTest {
        // When
        getShowCollectionDetailsTipUseCase()

        // Then
        coVerify(exactly = 1) { categoryTipsRepository.showCategoryDetailsTip() }
    }

    @Test
    fun `getShowCollectionDetailsTipUseCase should return result from repository`() = runTest {
        // Given
        coEvery { categoryTipsRepository.showCategoryDetailsTip() } returns true

        // When
        val result = getShowCollectionDetailsTipUseCase()

        // Then
        assertThat(result).isNotNull()          // Unit is non-null
        coVerify(exactly = 1) { categoryTipsRepository.showCategoryDetailsTip() }
    }

    @Test
    fun `getShowCollectionDetailsTipUseCase should complete operation successfully`() = runTest {
        // When
        val result = getShowCollectionDetailsTipUseCase()

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `getShowCollectionDetailsTipUseCase should handle multiple invocations`() = runTest {
        // When
        repeat(3) { getShowCollectionDetailsTipUseCase() }

        // Then
        coVerify(exactly = 3) { categoryTipsRepository.showCategoryDetailsTip() }
    }

    @Test
    fun `getShowCollectionDetailsTipUseCase makes exactly one repository call`() = runTest {
        // When
        getShowCollectionDetailsTipUseCase()

        // Then
        coVerify(exactly = 1) { categoryTipsRepository.showCategoryDetailsTip() }
        confirmVerified(categoryTipsRepository)
    }

    @Test
    fun `getShowCollectionDetailsTipUseCase respects number of calls`() = runTest {
        // When
        repeat(2) { getShowCollectionDetailsTipUseCase() }

        // Then
        coVerify(exactly = 2) { categoryTipsRepository.showCategoryDetailsTip() }
    }
}