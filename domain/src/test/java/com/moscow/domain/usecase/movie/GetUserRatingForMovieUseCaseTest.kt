package com.moscow.domain.usecase.movie

import com.google.common.truth.Truth.assertThat
import com.moscow.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetUserRatingForMovieUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var getUserRatingForMovieUseCase: GetUserRatingForMovieUseCase

    @BeforeEach
    fun setUp() {
        movieRepository = mockk(relaxed = true)
        getUserRatingForMovieUseCase = GetUserRatingForMovieUseCase(movieRepository)
    }

    @Test
    fun `getUserRatingForMovieUseCase should call repository method`() = runTest {
        // Given
        val movieId = 101

        // When
        getUserRatingForMovieUseCase(movieId)

        // Then
        coVerify(exactly = 1) { movieRepository.getUserRatingMovie(movieId = movieId) }
    }

    @Test
    fun `getUserRatingForMovieUseCase should complete operation successfully`() = runTest {
        // Given
        val movieId = 7
        coEvery { movieRepository.getUserRatingMovie(any()) } returns 0
        // When
        val result = getUserRatingForMovieUseCase(movieId)

        // Then
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun `getUserRatingForMovieUseCase should handle multiple invocations`() = runTest {
        // When
        repeat(3) { i -> getUserRatingForMovieUseCase(movieId = i + 1) }

        // Then
        coVerify(exactly = 3) { movieRepository.getUserRatingMovie(movieId = any()) }
    }

    @Test
    fun `getUserRatingForMovieUseCase makes exactly one repository call`() = runTest {
        // Given
        val movieId = 42

        // When
        getUserRatingForMovieUseCase(movieId)

        // Then
        coVerify(exactly = 1) { movieRepository.getUserRatingMovie(movieId) }
        confirmVerified(movieRepository)
    }
}