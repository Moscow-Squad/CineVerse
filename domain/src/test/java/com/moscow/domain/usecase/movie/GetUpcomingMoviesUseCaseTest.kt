package com.moscow.domain.usecase.movie

import com.google.common.truth.Truth.assertThat
import com.moscow.domain.repository.MovieRepository
import com.moscow.domain.model.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetUpcomingMoviesUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase

    @BeforeEach
    fun setUp() {
        movieRepository = mockk(relaxed = true)
        getUpcomingMoviesUseCase = GetUpcomingMoviesUseCase(movieRepository)
    }

    @Test
    fun `getUpcomingMoviesUseCase should call repository method`() = runTest {
        // Given
        val page = 1
        val forceRefresh = false

        // When
        getUpcomingMoviesUseCase(page, forceRefresh)

        // Then
        coVerify(exactly = 1) { movieRepository.getUpComingMovies(page = page, forceRefresh = forceRefresh) }
    }

    @Test
    fun `getUpcomingMoviesUseCase should return result from repository`() = runTest {
        // Given
        val page = 2
        val forceRefresh = true
        val expectedMovies = listOf(mockk<Movie>(), mockk())
        coEvery { movieRepository.getUpComingMovies(page, forceRefresh) } returns expectedMovies

        // When
        val result = getUpcomingMoviesUseCase(page, forceRefresh)

        // Then
        assertThat(result).isEqualTo(expectedMovies)
        coVerify(exactly = 1) { movieRepository.getUpComingMovies(page, forceRefresh) }
    }

    @Test
    fun `getUpcomingMoviesUseCase should complete operation successfully`() = runTest {
        // Given
        val page = 3
        val forceRefresh = false
        coEvery { movieRepository.getUpComingMovies(any(), any()) } returns emptyList()

        // When
        val result = getUpcomingMoviesUseCase(page, forceRefresh)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `getUpcomingMoviesUseCase should handle multiple invocations`() = runTest {
        // Given
        val forceRefresh = false

        // When
        repeat(3) { i -> getUpcomingMoviesUseCase(page = i + 1, forceRefresh = forceRefresh) }

        // Then
        coVerify(exactly = 3) { movieRepository.getUpComingMovies(page = any(), forceRefresh = forceRefresh) }
    }

    @Test
    fun `getUpcomingMoviesUseCase makes exactly one repository call`() = runTest {
        // Given
        val page = 5
        val forceRefresh = true

        // When
        getUpcomingMoviesUseCase(page, forceRefresh)

        // Then
        coVerify(exactly = 1) { movieRepository.getUpComingMovies(page, forceRefresh) }
        confirmVerified(movieRepository)
    }

    @Test
    fun `getUpcomingMoviesUseCase respects number of calls`() = runTest {
        // Given
        val page = 7
        val forceRefresh = false

        // When
        repeat(2) { getUpcomingMoviesUseCase(page, forceRefresh) }

        // Then
        coVerify(exactly = 2) { movieRepository.getUpComingMovies(page, forceRefresh) }
    }
}