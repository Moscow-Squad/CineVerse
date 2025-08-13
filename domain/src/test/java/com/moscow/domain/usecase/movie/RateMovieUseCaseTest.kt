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

class RateMovieUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var rateMovieUseCase: RateMovieUseCase

    @BeforeEach
    fun setUp() {
        movieRepository = mockk(relaxed = true)
        rateMovieUseCase = RateMovieUseCase(movieRepository)
    }

    @Test
    fun `rateMovieUseCase should call repository method`() = runTest {
        // Given
        val rating = 4.5f
        val movieId = 101

        // When
        rateMovieUseCase(rating, movieId)

        // Then
        coVerify(exactly = 1) {
            movieRepository.addRatingMovie(
                id = movieId,
                rating = rating
            )
        }
    }

    @Test
    fun `rateMovieUseCase should handle multiple invocations`() = runTest {
        // Given
        val rating = 2.5f
        val movieId = 9

        // When
        repeat(3) { rateMovieUseCase(rating, movieId) }

        // Then
        coVerify(exactly = 3) { movieRepository.addRatingMovie(id = movieId, rating = rating) }
    }

    @Test
    fun `rateMovieUseCase makes exactly one repository call`() = runTest {
        // Given
        val rating = 1.0f
        val movieId = 100

        // When
        rateMovieUseCase(rating, movieId)

        // Then
        coVerify(exactly = 1) { movieRepository.addRatingMovie(id = movieId, rating = rating) }
        confirmVerified(movieRepository)
    }
}