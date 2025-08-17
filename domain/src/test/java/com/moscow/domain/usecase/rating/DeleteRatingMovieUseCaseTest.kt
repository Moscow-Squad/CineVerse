package com.moscow.domain.usecase.rating

import com.moscow.domain.repository.MovieRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteRatingMovieUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var useCase: DeleteRatingMovieUseCase

    @BeforeEach
    fun setup() {
        movieRepository = mockk(relaxed = true)
        useCase = DeleteRatingMovieUseCase(movieRepository)
    }

    @Test
    fun `invoke should call deleteRatingMovie with correct movieId`() = runTest {
        val movieId = 101

        useCase(movieId)

        coVerify { movieRepository.deleteRatingMovie(movieId) }
    }
}