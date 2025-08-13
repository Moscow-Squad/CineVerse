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

class DeleteRatingMovieUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var deleteRatingMovieUseCase: DeleteRatingMovieUseCase

    @BeforeEach
    fun setUp() {
        movieRepository = mockk(relaxed = true)
        deleteRatingMovieUseCase = DeleteRatingMovieUseCase(movieRepository)
    }

    @Test
    fun `deleteRatingMovieUseCase should call repository method`() = runTest {
        // Given
        val movieId = 123

        // When
        deleteRatingMovieUseCase(movieId)

        // Then
        coVerify(exactly = 1) { movieRepository.deleteRatingMovie(movieId = movieId) }
    }

    @Test
    fun `deleteRatingMovieUseCase should return result from repository`() = runTest {
        // Given
        val movieId = 456
        coEvery { movieRepository.deleteRatingMovie(movieId = movieId) } returns Unit

        // When
        val result = deleteRatingMovieUseCase(movieId)

        // Then
        assertThat(result).isNotNull()          // Unit is non-null
        coVerify(exactly = 1) { movieRepository.deleteRatingMovie(movieId = movieId) }
    }

    @Test
    fun `deleteRatingMovieUseCase should complete operation successfully`() = runTest {
        // Given
        val movieId = 789

        // When
        val result = deleteRatingMovieUseCase(movieId)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `deleteRatingMovieUseCase should handle multiple invocations`() = runTest {
        // Given
        val movieId = 101

        // When
        repeat(3) { deleteRatingMovieUseCase(movieId) }

        // Then
        coVerify(exactly = 3) { movieRepository.deleteRatingMovie(movieId = movieId) }
    }

    @Test
    fun `deleteRatingMovieUseCase makes exactly one repository call`() = runTest {
        // Given
        val movieId = 202

        // When
        deleteRatingMovieUseCase(movieId)

        // Then
        coVerify(exactly = 1) { movieRepository.deleteRatingMovie(movieId = movieId) }
        confirmVerified(movieRepository)
    }

    @Test
    fun `deleteRatingMovieUseCase respects number of calls`() = runTest {
        // Given
        val movieId = 303

        // When
        repeat(2) { deleteRatingMovieUseCase(movieId) }

        // Then
        coVerify(exactly = 2) { movieRepository.deleteRatingMovie(movieId = movieId) }
    }
}