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

class GetPopularMoviesUseCaseTest {

    private lateinit var repository: MovieRepository
    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        getPopularMoviesUseCase = GetPopularMoviesUseCase(repository)
    }

    @Test
    fun `getPopularMoviesUseCase should call repository method`() = runTest {
        // Given
        val page = 1

        // When
        getPopularMoviesUseCase(page)

        // Then
        coVerify(exactly = 1) { repository.getPopularMovies(page) }
    }

    @Test
    fun `getPopularMoviesUseCase should return result from repository`() = runTest {
        // Given
        val page = 1
        val movie1 = mockk<Movie> { coEvery { id } returns 1 }
        val movie2 = mockk<Movie> { coEvery { id } returns 2 }
        val repositoryMovies = listOf(movie1, movie2)
        coEvery { repository.getPopularMovies(page) } returns repositoryMovies

        // When
        val result = getPopularMoviesUseCase(page)

        // Then
        assertThat(result).isEqualTo(repositoryMovies)
        coVerify(exactly = 1) { repository.getPopularMovies(page) }
    }

    @Test
    fun `getPopularMoviesUseCase should complete operation successfully`() = runTest {
        // Given
        val page = 2

        // When
        val result = getPopularMoviesUseCase(page)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `getPopularMoviesUseCase should handle multiple invocations`() = runTest {
        // Given
        val page = 1

        // When
        repeat(3) { getPopularMoviesUseCase(page) }

        // Then
        coVerify(exactly = 3) { repository.getPopularMovies(page) }
    }

    @Test
    fun `getPopularMoviesUseCase makes exactly one repository call`() = runTest {
        // Given
        val page = 3

        // When
        getPopularMoviesUseCase(page)

        // Then
        coVerify(exactly = 1) { repository.getPopularMovies(page) }
        confirmVerified(repository)
    }

    @Test
    fun `getPopularMoviesUseCase respects number of calls`() = runTest {
        // Given
        val page = 2

        // When
        repeat(2) { getPopularMoviesUseCase(page) }

        // Then
        coVerify(exactly = 2) { repository.getPopularMovies(page) }
    }
}