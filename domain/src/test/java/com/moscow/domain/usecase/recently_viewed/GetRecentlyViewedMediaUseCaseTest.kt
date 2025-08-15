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

class GetRecentlyViewedMediaUseCaseTest {

    private lateinit var repository: RecentlyViewedRepository
    private lateinit var getRecentlyViewedMediaUseCase: GetRecentlyViewedMediaUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        getRecentlyViewedMediaUseCase = GetRecentlyViewedMediaUseCase(repository)
    }

    @Test
    fun `getRecentlyViewedMediaUseCase should call repository method`() = runTest {
        // When
        getRecentlyViewedMediaUseCase()

        // Then
        coVerify(exactly = 1) { repository.getRecentlyViewedMedia() }
    }

    @Test
    fun `getRecentlyViewedMediaUseCase should return result from repository`() = runTest {
        // Given
        val expected = listOf(mockk<Any>(), mockk<Any>())
        //coEvery { repository.getRecentlyViewedMedia() } returns expected

        // When
        val result = getRecentlyViewedMediaUseCase()

        // Then
        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { repository.getRecentlyViewedMedia() }
    }

    @Test
    fun `getRecentlyViewedMediaUseCase should complete operation successfully`() = runTest {
        // Given
        //coEvery { repository.getRecentlyViewedMedia() } returns listOf<Any>()

        // When
        val result = getRecentlyViewedMediaUseCase()

        // Then
        assertThat(result).isNotNull()
        coVerify(exactly = 1) { repository.getRecentlyViewedMedia() }
    }

    @Test
    fun `getRecentlyViewedMediaUseCase should handle multiple invocations`() = runTest {
        // When
        repeat(3) { getRecentlyViewedMediaUseCase() }

        // Then
        coVerify(exactly = 3) { repository.getRecentlyViewedMedia() }
    }

    @Test
    fun `getRecentlyViewedMediaUseCase makes exactly one repository call`() = runTest {
        // When
        getRecentlyViewedMediaUseCase()

        // Then
        coVerify(exactly = 1) { repository.getRecentlyViewedMedia() }
        confirmVerified(repository)
    }

    @Test
    fun `getRecentlyViewedMediaUseCase respects number of calls`() = runTest {
        // When
        repeat(2) { getRecentlyViewedMediaUseCase() }

        // Then
        coVerify(exactly = 2) { repository.getRecentlyViewedMedia() }
    }
}