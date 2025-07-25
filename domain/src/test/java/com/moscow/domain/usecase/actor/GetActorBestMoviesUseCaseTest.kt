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
                name = "Top Hit",
                genreIds = listOf(1, 2),
                rating = 8.7f,
                releaseDate = LocalDate(2020, 5, 20),
                adult = false,
                backdropPath = "/backdrop/top_hit.jpg",
                originalLanguage = "en",
                originalTitle = "Top Hit Original",
                overview = "A blockbuster movie loved by fans.",
                posterPath = "/poster/top_hit.jpg",
                video = false,
                poster = "https://image.tmdb.org/t/p/w500/top_hit.jpg"
            ),
            Movie(
                id = 102,
                name = "Box Office Legend",
                genreIds = listOf(4, 7),
                rating = 9.1f,
                releaseDate = LocalDate(2019, 11, 10),
                adult = false,
                backdropPath = "/backdrop/legend.jpg",
                originalLanguage = "en",
                originalTitle = "Legend of Cinema",
                overview = "Critically acclaimed with global success.",
                posterPath = "/poster/legend.jpg",
                video = true,
                poster = "https://image.tmdb.org/t/p/w500/legend.jpg"
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
