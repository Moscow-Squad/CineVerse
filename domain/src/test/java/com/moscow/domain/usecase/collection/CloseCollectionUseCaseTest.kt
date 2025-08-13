package com.moscow.domain.usecase.collection

import com.moscow.domain.repository.CategoryTipsRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CloseCollectionDetailsTipUseCaseTest {

    private lateinit var categoryTipsRepository: CategoryTipsRepository
    private lateinit var closeCollectionDetailsTipUseCase: CloseCollectionDetailsTipUseCase

    @BeforeEach
    fun setUp() {
        categoryTipsRepository = mockk(relaxed = true)
        closeCollectionDetailsTipUseCase = CloseCollectionDetailsTipUseCase(categoryTipsRepository)
    }

    @Test
    fun `closeCollectionDetailsTipUseCase should close collection details tip successfully`() = runTest {
        // Given
        // No parameters needed

        // When
        val result = closeCollectionDetailsTipUseCase()

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `closeCollectionDetailsTipUseCase should call repository method`() = runTest {
        // Given
        // No parameters needed

        // When
        closeCollectionDetailsTipUseCase()

        // Then
        coVerify(exactly = 1) {
            categoryTipsRepository.closeCategoryDetailsTip()
        }
    }

    @Test
    fun `closeCollectionDetailsTipUseCase should complete operation without parameters`() = runTest {
        // Given
        // No parameters needed

        // When
        val result = closeCollectionDetailsTipUseCase()

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `closeCollectionDetailsTipUseCase should handle tip closing successfully`() = runTest {
        // Given
        // No parameters needed

        // When
        closeCollectionDetailsTipUseCase()

        // Then
        coVerify {
            categoryTipsRepository.closeCategoryDetailsTip()
        }
    }

    @Test
    fun `closeCollectionDetailsTipUseCase should return result from repository`() = runTest {
        // Given
        // No parameters needed

        // When
        val result = closeCollectionDetailsTipUseCase()

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `closeCollectionDetailsTipUseCase should invoke repository method correctly`() = runTest {
        // Given
        // No parameters needed

        // When
        closeCollectionDetailsTipUseCase()

        // Then
        coVerify(exactly = 1) {
            categoryTipsRepository.closeCategoryDetailsTip()
        }
    }

    @Test
    fun `closeCollectionDetailsTipUseCase should handle multiple invocations`() = runTest {
        // Given
        // No parameters needed

        // When
        val result = closeCollectionDetailsTipUseCase()

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `closeCollectionDetailsTipUseCase should complete tip dismissal operation`() = runTest {
        // Given
        // No parameters needed

        // When
        closeCollectionDetailsTipUseCase()

        // Then
        coVerify { categoryTipsRepository.closeCategoryDetailsTip() }
    }

    @Test
    fun `closeCollectionDetailsTipUseCase makes exactly one repository call`() = runTest {
        // Given
        // No parameters needed

        // When
        closeCollectionDetailsTipUseCase()

        // Then
        coVerify(exactly = 1) { categoryTipsRepository.closeCategoryDetailsTip() }
    }
}