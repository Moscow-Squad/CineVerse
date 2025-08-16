package com.moscow.domain.usecase.actor

import com.moscow.domain.model.Movie
import com.moscow.domain.repository.ActorRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetActorBestMoviesUseCaseTest {

    private lateinit var actorRepository: ActorRepository
    private lateinit var useCase: GetActorBestMoviesUseCase

    @BeforeEach
    fun setup() {
        actorRepository = mockk()
        useCase = GetActorBestMoviesUseCase(actorRepository)
    }

    @Test
    fun `invoke should return sorted and distinct best movies for actor`() = runTest {
        val actorId = 1
        val duplicateMovie = Movie(
            id = 101,
            title = "Top Hit",
            overview = "A blockbuster movie loved by fans.",
            trailerUrl = "https://image.tmdb.org/t/p/w500/top_hit.jpg",
            backdropUrl = "/backdrop/top_hit.jpg",
            posterUrl = "https://image.tmdb.org/t/p/w500/top_hit.jpg",
            rating = 8.7f,
            genreIds = listOf(1, 2),
            releaseDate = LocalDate(2020, 5, 20),
            genres = listOf("Action", "Adventure"),
            duration = Movie.Duration(hours = 2, minutes = 30)
        )

        val expectedMovies = listOf(
            duplicateMovie,
            duplicateMovie.copy(id = 102, title = "Box Office Legend")
        )

        val inputMovies = listOf(
            duplicateMovie,
            duplicateMovie.copy(id = 102, title = "Box Office Legend"),
            duplicateMovie
        )

        coEvery { actorRepository.getBestOfMovies(actorId) } returns inputMovies

        val result = useCase(actorId)

        assertEquals(expectedMovies, result)
        coVerify(exactly = 1) { actorRepository.getBestOfMovies(actorId) }
    }
}