package com.moscow.domain.usecase.actor

import com.moscow.domain.model.Genre
import com.moscow.domain.model.Movie
import com.moscow.domain.repository.ActorRepository
import io.mockk.coEvery
import org.junit.jupiter.api.Test
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import kotlin.test.assertEquals

class GetActorBestMoviesUseCaseTest {

    private lateinit var actorRepository: ActorRepository
    private lateinit var useCase: GetActorBestMoviesUseCase

    @BeforeEach
    fun setup() {
        actorRepository = mockk()
        useCase = GetActorBestMoviesUseCase(actorRepository)
    }

//    @Test
//    fun `invoke should return best movies for actor`() = runTest {
//        val actorId = 1
//        val expectedMovies = listOf(
//            Movie(
//                id = 101,
//                title = "Top Hit",
//                overview = "A blockbuster movie loved by fans.",
//                trailerUrl = "https://image.tmdb.org/t/p/w500/top_hit.jpg",
//                backdropPath = "/backdrop/top_hit.jpg",
//                posterPath = "https://image.tmdb.org/t/p/w500/top_hit.jpg",
//                rating = 8.7f,
//                genreIds = listOf(1, 2),
//                releaseDate = LocalDate(2020, 5, 20),
//                voteAverage = 0.5,
//                genres = listOf(
//                    Genre(id = 1, name = "Action"),
//                    Genre(id = 2, name = "Adventure")
//                ),
//                duration = Movie.Duration(hours = 2, minutes = 30)
//            ),
//            Movie(
//                id = 102,
//                title = "Box Office Legend",
//                overview = "Critically acclaimed with global success.",
//                trailerUrl = "https://image.tmdb.org/t/p/w500/top_hit.jpg",
//                backdropPath = "/backdrop/top_hit.jpg",
//                posterPath = "https://image.tmdb.org/t/p/w500/top_hit.jpg",
//                rating = 8.7f,
//                genreIds = listOf(1, 2),
//                releaseDate = LocalDate(2020, 5, 20),
//                voteAverage = 0.5,
//                genres = listOf(
//                    Genre(id = 1, name = "Action"),
//                    Genre(id = 2, name = "Adventure")
//                ),
//                duration = Movie.Duration(hours = 2, minutes = 30)
//            )
//        )
//        coEvery { actorRepository.getBestOfMovies(actorId) } returns expectedMovies
//
//        val result = actorRepository.getBestOfMovies(actorId)
//
//        assertEquals(expectedMovies, result)
//        coVerify(exactly = 1) { actorRepository.getBestOfMovies(actorId) }
//    }

}
