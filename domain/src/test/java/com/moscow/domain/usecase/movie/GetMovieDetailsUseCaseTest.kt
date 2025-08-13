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

class GetMovieDetailsUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @BeforeEach
    fun setUp() {
        movieRepository = mockk(relaxed = true)
        getMovieDetailsUseCase = GetMovieDetailsUseCase(movieRepository)
    }

    @Test
    fun `getMovieDetailsUseCase should call repository method`() = runTest {
        // Given
        val id = 123

        // When
        getMovieDetailsUseCase(id)

        // Then
        coVerify(exactly = 1) { movieRepository.getDetailsMovie(id = id) }
    }

    @Test
    fun `getMovieDetailsUseCase should return result from repository`() = runTest {
        // Given
        val id = 456
        val expectedMovie = mockk<Movie>()
        coEvery { movieRepository.getDetailsMovie(id = id) } returns expectedMovie

        // When
        val result = getMovieDetailsUseCase(id)

        // Then
        assertThat(result).isEqualTo(expectedMovie)
        coVerify(exactly = 1) { movieRepository.getDetailsMovie(id = id) }
    }

    @Test
    fun `getMovieDetailsUseCase should complete operation successfully`() = runTest {
        // Given
        val id = 789

        // When
        val result = getMovieDetailsUseCase(id)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `getMovieDetailsUseCase should handle multiple invocations`() = runTest {
        // Given
        val id = 101

        // When
        repeat(3) { getMovieDetailsUseCase(id) }

        // Then
        coVerify(exactly = 3) { movieRepository.getDetailsMovie(id = id) }
    }

    @Test
    fun `getMovieDetailsUseCase makes exactly one repository call`() = runTest {
        // Given
        val id = 202

        // When
        getMovieDetailsUseCase(id)

        // Then
        coVerify(exactly = 1) { movieRepository.getDetailsMovie(id = id) }
        confirmVerified(movieRepository)
    }

    @Test
    fun `getMovieDetailsUseCase respects number of calls`() = runTest {
        // Given
        val id = 303

        // When
        repeat(2) { getMovieDetailsUseCase(id) }

        // Then
        coVerify(exactly = 2) { movieRepository.getDetailsMovie(id = id) }
    }
}