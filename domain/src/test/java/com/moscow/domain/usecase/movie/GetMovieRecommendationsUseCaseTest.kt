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

class GetMovieRecommendationsUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var getMovieRecommendationsUseCase: GetMovieRecommendationsUseCase

    @BeforeEach
    fun setUp() {
        movieRepository = mockk(relaxed = true)
        getMovieRecommendationsUseCase = GetMovieRecommendationsUseCase(movieRepository)
    }

    @Test
    fun `getMovieRecommendationsUseCase should call repository method`() = runTest {
        // Given
        val id = 123
        val page = 1

        // When
        getMovieRecommendationsUseCase(id, page)

        // Then
        coVerify(exactly = 1) { movieRepository.getRecommendationsMovie(id = id, page = page) }
    }

    @Test
    fun `getMovieRecommendationsUseCase should return result from repository`() = runTest {
        // Given
        val id = 456
        val page = 1
        val expectedMovies = listOf<Movie>(mockk(), mockk())
        coEvery { movieRepository.getRecommendationsMovie(id = id, page = page) } returns expectedMovies

        // When
        val result = getMovieRecommendationsUseCase(id, page)

        // Then
        assertThat(result).isEqualTo(expectedMovies)
        coVerify(exactly = 1) { movieRepository.getRecommendationsMovie(id = id, page = page) }
    }

    @Test
    fun `getMovieRecommendationsUseCase should complete operation successfully`() = runTest {
        // Given
        val id = 789
        val page = 2

        // When
        val result = getMovieRecommendationsUseCase(id, page)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `getMovieRecommendationsUseCase should handle multiple invocations`() = runTest {
        // Given
        val id = 101
        val page = 1

        // When
        repeat(3) { getMovieRecommendationsUseCase(id, page) }

        // Then
        coVerify(exactly = 3) { movieRepository.getRecommendationsMovie(id = id, page = page) }
    }

    @Test
    fun `getMovieRecommendationsUseCase makes exactly one repository call`() = runTest {
        // Given
        val id = 202
        val page = 3

        // When
        getMovieRecommendationsUseCase(id, page)

        // Then
        coVerify(exactly = 1) { movieRepository.getRecommendationsMovie(id = id, page = page) }
        confirmVerified(movieRepository)
    }

    @Test
    fun `getMovieRecommendationsUseCase respects number of calls`() = runTest {
        // Given
        val id = 303
        val page = 2

        // When
        repeat(2) { getMovieRecommendationsUseCase(id, page) }

        // Then
        coVerify(exactly = 2) { movieRepository.getRecommendationsMovie(id = id, page = page) }
    }
}