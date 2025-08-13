package com.moscow.domain.usecase.movie

import com.google.common.truth.Truth.assertThat
import com.moscow.domain.model.Movie
import com.moscow.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetRecentlyReleasedMoviesUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var getRecentlyReleasedMoviesUseCase: GetRecentlyReleasedMoviesUseCase

    @BeforeEach
    fun setUp() {
        movieRepository = mockk(relaxed = true)
        getRecentlyReleasedMoviesUseCase = GetRecentlyReleasedMoviesUseCase(movieRepository)
    }

    @Test
    fun `getRecentlyReleasedMoviesUseCase should call repository method`() = runTest {
        // Given
        val page = 1
        val forceRefresh = false

        // When
        getRecentlyReleasedMoviesUseCase(page, forceRefresh)

        // Then
        coVerify(exactly = 1) { movieRepository.getRecentlyReleasedMovies(page = page, forceRefresh = forceRefresh) }
    }

    @Test
    fun `getRecentlyReleasedMoviesUseCase should return result from repository`() = runTest {
        // Given
        val page = 1
        val forceRefresh = true
        val expectedMovies = listOf<Movie>(mockk(), mockk())
        coEvery { movieRepository.getRecentlyReleasedMovies(page = page, forceRefresh = forceRefresh) } returns expectedMovies

        // When
        val result = getRecentlyReleasedMoviesUseCase(page, forceRefresh)

        // Then
        assertThat(result).isEqualTo(expectedMovies)
        coVerify(exactly = 1) { movieRepository.getRecentlyReleasedMovies(page = page, forceRefresh = forceRefresh) }
    }

    @Test
    fun `getRecentlyReleasedMoviesUseCase should complete operation successfully`() = runTest {
        // Given
        val page = 2

        // When
        val result = getRecentlyReleasedMoviesUseCase(page)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `getRecentlyReleasedMoviesUseCase should handle multiple invocations`() = runTest {
        // Given
        val page = 1
        val forceRefresh = false

        // When
        repeat(3) { getRecentlyReleasedMoviesUseCase(page, forceRefresh) }

        // Then
        coVerify(exactly = 3) { movieRepository.getRecentlyReleasedMovies(page = page, forceRefresh = forceRefresh) }
    }

    @Test
    fun `getRecentlyReleasedMoviesUseCase makes exactly one repository call`() = runTest {
        // Given
        val page = 3
        val forceRefresh = true

        // When
        getRecentlyReleasedMoviesUseCase(page, forceRefresh)

        // Then
        coVerify(exactly = 1) { movieRepository.getRecentlyReleasedMovies(page = page, forceRefresh = forceRefresh) }
        confirmVerified(movieRepository)
    }

    @Test
    fun `getRecentlyReleasedMoviesUseCase respects number of calls`() = runTest {
        // Given
        val page = 2
        val forceRefresh = false

        // When
        repeat(2) { getRecentlyReleasedMoviesUseCase(page, forceRefresh) }

        // Then
        coVerify(exactly = 2) { movieRepository.getRecentlyReleasedMovies(page = page, forceRefresh = forceRefresh) }
    }
}