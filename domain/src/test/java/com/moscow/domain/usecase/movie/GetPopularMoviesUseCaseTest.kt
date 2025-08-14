package com.moscow.domain.usecase.movie

import com.moscow.domain.model.Movie
import com.moscow.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPopularMoviesUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var useCase: GetPopularMoviesUseCase

    @BeforeEach
    fun setup() {
        movieRepository = mockk()
        useCase = GetPopularMoviesUseCase(movieRepository)
    }

    @Test
    fun `invoke should return distinct popular movies`() = runTest {
        val page = 1
        val movie = Movie(
            id = 1,
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

        val movies = listOf(
            movie,
            movie.copy(id = 2),
            movie.copy(id = 1)
        )

        coEvery { movieRepository.getPopularMovies(page) } returns movies

        val result = useCase(page)

        assertEquals(2, result.size)
        assertEquals(setOf(1, 2), result.map { it.id }.toSet())
    }
}