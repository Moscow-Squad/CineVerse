package com.moscow.domain.usecase.movie

import com.google.common.truth.Truth.assertThat
import com.moscow.domain.model.CreditsInfo
import com.moscow.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetMovieCreditsUseCaseTest {

    private lateinit var detailsRepository: MovieRepository
    private lateinit var getMovieCreditsUseCase: GetMovieCreditsUseCase

    @BeforeEach
    fun setUp() {
        detailsRepository = mockk(relaxed = true)
        getMovieCreditsUseCase = GetMovieCreditsUseCase(detailsRepository)
    }

    @Test
    fun `getMovieCreditsUseCase should call repository method`() = runTest {
        // Given
        val id = 123

        // When
        getMovieCreditsUseCase(id)

        // Then
        coVerify(exactly = 1) { detailsRepository.getCreditsMovie(id = id) }
    }

    @Test
    fun `getMovieCreditsUseCase should return result from repository`() = runTest {
        // Given
        val id = 456
        val expectedCreditsInfo = mockk<CreditsInfo>()
        coEvery { detailsRepository.getCreditsMovie(id = id) } returns expectedCreditsInfo

        // When
        val result = getMovieCreditsUseCase(id)

        // Then
        assertThat(result).isEqualTo(expectedCreditsInfo)
        coVerify(exactly = 1) { detailsRepository.getCreditsMovie(id = id) }
    }

    @Test
    fun `getMovieCreditsUseCase should complete operation successfully`() = runTest {
        // Given
        val id = 789

        // When
        val result = getMovieCreditsUseCase(id)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `getMovieCreditsUseCase should handle multiple invocations`() = runTest {
        // Given
        val id = 101

        // When
        repeat(3) { getMovieCreditsUseCase(id) }

        // Then
        coVerify(exactly = 3) { detailsRepository.getCreditsMovie(id = id) }
    }

    @Test
    fun `getMovieCreditsUseCase makes exactly one repository call`() = runTest {
        // Given
        val id = 202

        // When
        getMovieCreditsUseCase(id)

        // Then
        coVerify(exactly = 1) { detailsRepository.getCreditsMovie(id = id) }
        confirmVerified(detailsRepository)
    }

    @Test
    fun `getMovieCreditsUseCase respects number of calls`() = runTest {
        // Given
        val id = 303

        // When
        repeat(2) { getMovieCreditsUseCase(id) }

        // Then
        coVerify(exactly = 2) { detailsRepository.getCreditsMovie(id = id) }
    }
}