package com.moscow.domain.usecase.rating

import com.moscow.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserRatingForMovieUseCaseTest {

    private val movieRepository: MovieRepository = mockk()
    private lateinit var useCase: GetUserRatingForMovieUseCase

    @BeforeEach
    fun setup() {
        useCase = GetUserRatingForMovieUseCase(movieRepository)
    }

    @Test
    fun `invoke should return user rating for movie`() = runTest {
        val movieId = 321
        val expectedRating = 21254
        coEvery { movieRepository.getUserRatingMovie(movieId) } returns expectedRating

        val result = useCase(movieId)

        assertEquals(expectedRating, result)
        coVerify { movieRepository.getUserRatingMovie(movieId) }
    }
}