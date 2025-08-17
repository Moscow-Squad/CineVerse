package com.moscow.domain.usecase.match

import com.moscow.domain.model.Movie
import com.moscow.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetMatchedMoviesTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var useCase: GetMatchedMovies

    @BeforeEach
    fun setup() {
        movieRepository = mockk()
        useCase = GetMatchedMovies(movieRepository)
    }

    @Test
    fun `invoke should return matched movies based on filters`() = runTest {
        val page = 1
        val genres = "28,12"
        val runtimeGte = 90
        val runtimeLte = 150
        val releaseDateGte = "2020-01-01"
        val releaseDateLte = "2023-12-31"

        val movie = Movie(
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
            movie,
            movie.copy(id = 102)
        )

        coEvery {
            movieRepository.getMatchedMovies(
                page = page,
                genres = genres,
                runtimeGte = runtimeGte,
                runtimeLte = runtimeLte,
                releaseDateGte = releaseDateGte,
                releaseDateLte = releaseDateLte
            )
        } returns expectedMovies

        val result = useCase(
            page = page,
            genres = genres,
            runtimeGte = runtimeGte,
            runtimeLte = runtimeLte,
            releaseDateGte = releaseDateGte,
            releaseDateLte = releaseDateLte
        )

        assertEquals(expectedMovies, result)
        coVerify(exactly = 1) {
            movieRepository.getMatchedMovies(
                page = page,
                genres = genres,
                runtimeGte = runtimeGte,
                runtimeLte = runtimeLte,
                releaseDateGte = releaseDateGte,
                releaseDateLte = releaseDateLte
            )
        }
    }
}