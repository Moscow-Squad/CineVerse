package com.moscow.domain.usecase.rating

import com.moscow.domain.repository.MovieRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RateMovieUseCaseTest {

    private val movieRepository: MovieRepository = mockk()
    private lateinit var useCase: RateMovieUseCase

    @BeforeEach
    fun setup() {
        useCase = RateMovieUseCase(movieRepository)
    }

    @Test
    fun `invoke should call addRatingMovie with correct rating and movieId`() = runTest {
        val rating = 3.0f
        val movieId = 456
        coEvery { movieRepository.addRatingMovie(id = movieId, rating = rating) } just Runs

        useCase(rating, movieId)

        coVerify { movieRepository.addRatingMovie(id = movieId, rating = rating) }
    }
}