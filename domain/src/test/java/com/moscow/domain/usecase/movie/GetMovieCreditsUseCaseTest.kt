package com.moscow.domain.usecase.movie

import com.moscow.domain.model.CreditsInfo
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
class GetMovieCreditsUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var useCase: GetMovieCreditsUseCase

    @BeforeEach
    fun setup() {
        movieRepository = mockk()
        useCase = GetMovieCreditsUseCase(movieRepository)
    }

    @Test
    fun `invoke should return movie credits`() = runTest {
        val movieId = 10
        val expectedCredits = CreditsInfo(
            cast = listOf(
                CreditsInfo.CastInfo(
                    id = 1,
                    originalName = "Robert Downey Jr.",
                    characterName = "Tony Stark",
                    profileImg = "https://image.tmdb.org/t/p/w500/robert.jpg"
                )
            ),
            crew = listOf(
                CreditsInfo.CrewInfo(
                    id = 2,
                    name = "Jon Favreau",
                    job = "Director"
                )
            )
        )

        coEvery { movieRepository.getCreditsMovie(movieId) } returns expectedCredits

        val result = useCase(movieId)

        assertEquals(expectedCredits, result)
        coVerify { movieRepository.getCreditsMovie(movieId) }
    }
}