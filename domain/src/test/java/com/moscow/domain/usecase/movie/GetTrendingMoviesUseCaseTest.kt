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

class GetTrendingMoviesUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var getTrendingMoviesUseCase: GetTrendingMoviesUseCase

    @BeforeEach
    fun setUp() {
        movieRepository = mockk(relaxed = true)
        getTrendingMoviesUseCase = GetTrendingMoviesUseCase(movieRepository)
    }

    @Test
    fun `getTrendingMoviesUseCase should call repository method`() = runTest {
        // Given
        val forceRefresh = false

        // When
        getTrendingMoviesUseCase(forceRefresh)

        // Then
        coVerify(exactly = 1) { movieRepository.getTrendingMovies(forceRefresh) }
    }

    @Test
    fun `getTrendingMoviesUseCase should return result from repository`() = runTest {
        // Given
        val forceRefresh = true
        val expectedMovies = listOf<Movie>(mockk(), mockk())
        coEvery { movieRepository.getTrendingMovies(forceRefresh) } returns expectedMovies

        // When
        val result = getTrendingMoviesUseCase(forceRefresh)

        // Then
        assertThat(result).isEqualTo(expectedMovies)
        coVerify(exactly = 1) { movieRepository.getTrendingMovies(forceRefresh) }
    }

    @Test
    fun `getTrendingMoviesUseCase should complete operation successfully`() = runTest {
        // When
        val result = getTrendingMoviesUseCase()

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `getTrendingMoviesUseCase should handle multiple invocations`() = runTest {
        // Given
        val forceRefresh = false

        // When
        repeat(3) { getTrendingMoviesUseCase(forceRefresh) }

        // Then
        coVerify(exactly = 3) { movieRepository.getTrendingMovies(forceRefresh) }
    }

    @Test
    fun `getTrendingMoviesUseCase makes exactly one repository call`() = runTest {
        // Given
        val forceRefresh = true

        // When
        getTrendingMoviesUseCase(forceRefresh)

        // Then
        coVerify(exactly = 1) { movieRepository.getTrendingMovies(forceRefresh) }
        confirmVerified(movieRepository)
    }

    @Test
    fun `getTrendingMoviesUseCase respects number of calls`() = runTest {
        // Given
        val forceRefresh = false

        // When
        repeat(2) { getTrendingMoviesUseCase(forceRefresh) }

        // Then
        coVerify(exactly = 2) { movieRepository.getTrendingMovies(forceRefresh) }
    }
}