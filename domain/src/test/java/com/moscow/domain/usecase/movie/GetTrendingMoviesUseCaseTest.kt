package com.moscow.domain.usecase.movie

import com.moscow.domain.model.Movie
import com.moscow.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetTrendingMoviesUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var useCase: GetTrendingMoviesUseCase

    @BeforeEach
    fun setup() {
        movieRepository = mockk()
        useCase = GetTrendingMoviesUseCase(movieRepository)
    }

    @Test
    fun `invoke should return trending movies`() = runTest {
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

        val expected = listOf(
            movie,
            movie.copy(id = 102)
        )

        coEvery { movieRepository.getTrendingMovies(false) } returns expected

        val result = useCase()

        assertEquals(expected, result)
        coVerify { movieRepository.getTrendingMovies(false) }
    }
}