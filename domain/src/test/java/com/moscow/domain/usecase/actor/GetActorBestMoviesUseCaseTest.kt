package com.moscow.domain.usecase.actor

import com.moscow.domain.model.Movie
import com.moscow.domain.repository.ActorRepository
import io.mockk.coEvery
import org.junit.jupiter.api.Test
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.jupiter.api.BeforeEach

class GetActorBestMoviesUseCaseTest {

    private lateinit var actorRepository: ActorRepository
    private lateinit var useCase: GetActorBestMoviesUseCase

    @BeforeEach
    fun setup() {
        actorRepository = mockk()
        useCase = GetActorBestMoviesUseCase(actorRepository)
    }


    @Test
    fun `invoke should return best movies for actor`() = runTest {
        // Given
        val actorId = 1
        val expectedMovies = listOf(
            Movie(
                id = 101,
                title = "Top Hit",
                overview = "A blockbuster movie loved by fans.",
                trailerUrl = "",
                backdropPath = "/backdrop/top_hit.jpg",
                posterPath = "/poster/top_hit.jpg",
                releaseDate = LocalDate(2020, 5, 20),
                rating = 8.7f,
                genreIds = listOf(1, 2),
                genres = listOf("Action", "Drama"),
                duration = Movie.Duration(hours = 2, minutes = 10)
            ),
            Movie(
                id = 102,
                title = "Box Office Legend",
                overview = "Critically acclaimed with global success.",
                trailerUrl = "",
                backdropPath = "/backdrop/legend.jpg",
                posterPath = "/poster/legend.jpg",
                releaseDate = LocalDate(2019, 11, 10),
                rating = 9.1f,
                genreIds = listOf(4, 7),
                genres = listOf("Adventure", "Fantasy"),
                duration = Movie.Duration(hours = 2, minutes = 5)
            )
        )
        coEvery { actorRepository.getBestOfMovies(actorId) } returns expectedMovies

        // When
        val result = actorRepository.getBestOfMovies(actorId)

        // Then
        assertEquals(expectedMovies, result)
        coVerify(exactly = 1) { actorRepository.getBestOfMovies(actorId) }
    }

}
