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
class DeleteRatingMovieUseCaseTest {

    private val movieRepository: MovieRepository = mockk()
    private lateinit var useCase: DeleteRatingMovieUseCase

    @BeforeEach
    fun setup() {
        useCase = DeleteRatingMovieUseCase(movieRepository)
    }

    @Test
    fun `invoke should call deleteRatingMovie with correct movieId`() = runTest {
        val movieId = 101
        coEvery { movieRepository.deleteRatingMovie(movieId) } just Runs

        useCase(movieId)

        coVerify(exactly = 1) { movieRepository.deleteRatingMovie(movieId) }
    }
}