package com.moscow.domain.usecase.recently_viewed

import com.moscow.domain.model.Series
import com.google.common.truth.Truth.assertThat
import com.moscow.domain.repository.RecentlyViewedRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddRecentlyViewedSeriesUseCaseTest {

    private lateinit var repository: RecentlyViewedRepository
    private lateinit var addRecentlyViewedSeriesUseCase: AddRecentlyViewedSeriesUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        addRecentlyViewedSeriesUseCase = AddRecentlyViewedSeriesUseCase(repository)
    }

    @Test
    fun `addRecentlyViewedSeriesUseCase should call repository method`() = runTest {
        // Given
        val series = mockk<Series>()

        // When
        addRecentlyViewedSeriesUseCase(series)

        // Then
        coVerify(exactly = 1) { repository.addRecentlyViewedSeries(series = series) }
    }

    @Test
    fun `addRecentlyViewedSeriesUseCase should return result from repository`() = runTest {
        // Given
        val series = mockk<Series>()
        coEvery { repository.addRecentlyViewedSeries(series) } returns Unit

        // When
        val result = addRecentlyViewedSeriesUseCase(series)

        // Then
        assertThat(result).isNotNull() // Unit is non-null
        coVerify(exactly = 1) { repository.addRecentlyViewedSeries(series) }
    }

    @Test
    fun `addRecentlyViewedSeriesUseCase should complete operation successfully`() = runTest {
        // Given
        val series = mockk<Series>()
        coEvery { repository.addRecentlyViewedSeries(any()) } returns Unit

        // When
        val result = addRecentlyViewedSeriesUseCase(series)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `addRecentlyViewedSeriesUseCase should handle multiple invocations`() = runTest {
        // Given
        val series = mockk<Series>()

        // When
        repeat(3) { addRecentlyViewedSeriesUseCase(series) }

        // Then
        coVerify(exactly = 3) { repository.addRecentlyViewedSeries(series) }
    }

    @Test
    fun `addRecentlyViewedSeriesUseCase makes exactly one repository call`() = runTest {
        // Given
        val series = mockk<Series>()

        // When
        addRecentlyViewedSeriesUseCase(series)

        // Then
        coVerify(exactly = 1) { repository.addRecentlyViewedSeries(series) }
        confirmVerified(repository)
    }

    @Test
    fun `addRecentlyViewedSeriesUseCase respects number of calls`() = runTest {
        // Given
        val series = mockk<Series>()

        // When
        repeat(2) { addRecentlyViewedSeriesUseCase(series) }

        // Then
        coVerify(exactly = 2) { repository.addRecentlyViewedSeries(series) }
    }
}