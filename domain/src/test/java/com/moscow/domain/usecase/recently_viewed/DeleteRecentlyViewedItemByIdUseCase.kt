package com.moscow.domain.usecase.recently_viewed


import com.google.common.truth.Truth.assertThat
import com.moscow.domain.repository.RecentlyViewedRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteRecentlyViewedItemByIdUseCaseTest {

    private lateinit var repository: RecentlyViewedRepository
    private lateinit var deleteRecentlyViewedItemByIdUseCase: DeleteRecentlyViewedItemByIdUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        deleteRecentlyViewedItemByIdUseCase = DeleteRecentlyViewedItemByIdUseCase(repository)
    }

    @Test
    fun `deleteRecentlyViewedItemByIdUseCase should call repository method`() = runTest {
        // Given
        val id = 123

        // When
        deleteRecentlyViewedItemByIdUseCase(id)

        // Then
        coVerify(exactly = 1) { repository.deleteRecentlyViewedItemById(id = id) }
    }

    @Test
    fun `deleteRecentlyViewedItemByIdUseCase should return result from repository`() = runTest {
        // Given
        val id = 456
        coEvery { repository.deleteRecentlyViewedItemById(id) } returns Unit

        // When
        val result = deleteRecentlyViewedItemByIdUseCase(id)

        // Then
        assertThat(result).isNotNull() // Unit is non-null
        coVerify(exactly = 1) { repository.deleteRecentlyViewedItemById(id) }
    }

    @Test
    fun `deleteRecentlyViewedItemByIdUseCase should complete operation successfully`() = runTest {
        // Given
        val id = 789
        coEvery { repository.deleteRecentlyViewedItemById(any()) } returns Unit

        // When
        val result = deleteRecentlyViewedItemByIdUseCase(id)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `deleteRecentlyViewedItemByIdUseCase should handle multiple invocations`() = runTest {
        // Given
        val id = 101

        // When
        repeat(3) { deleteRecentlyViewedItemByIdUseCase(id) }

        // Then
        coVerify(exactly = 3) { repository.deleteRecentlyViewedItemById(id = id) }
    }

    @Test
    fun `deleteRecentlyViewedItemByIdUseCase makes exactly one repository call`() = runTest {
        // Given
        val id = 202

        // When
        deleteRecentlyViewedItemByIdUseCase(id)

        // Then
        coVerify(exactly = 1) { repository.deleteRecentlyViewedItemById(id = id) }
        confirmVerified(repository)
    }

    @Test
    fun `deleteRecentlyViewedItemByIdUseCase respects number of calls`() = runTest {
        // Given
        val id = 303

        // When
        repeat(2) { deleteRecentlyViewedItemByIdUseCase(id) }

        // Then
        coVerify(exactly = 2) { repository.deleteRecentlyViewedItemById(id = id) }
    }
}