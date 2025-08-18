package com.moscow.domain.usecase.rating

import com.moscow.domain.model.Movie
import com.moscow.domain.model.UserType
import com.moscow.domain.repository.MovieRepository
import com.moscow.domain.repository.auth.UserRepository
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
class GetRatedMoviesUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var userRepository: UserRepository
    private lateinit var useCase: GetRatedMoviesUseCase

    @BeforeEach
    fun setup() {
        movieRepository = mockk()
        userRepository = mockk()
        useCase = GetRatedMoviesUseCase(movieRepository, userRepository)
    }

    @Test
    fun `invoke should parse embedded userId and return rated movies`() = runTest {
        val page = 1
        val user = UserType.AuthenticatedUser(
            id = "id=42,token=abc",
            name = "Ahmed",
            username = "Ahmed",
            sessionId = "123",
            image = null,
            recentlyCollectionId = 1
        )

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
            GetRatedMoviesUseCase.RatedMovieResult(movie, 4.5f)
        )

        coEvery { userRepository.getUser() } returns user
        coEvery { movieRepository.getRatedMovies(42, page) } returns expected

        val result = useCase(page)

        assertEquals(expected, result)
        coVerify { movieRepository.getRatedMovies(42, page) }
    }

    @Test
    fun `invoke should fallback to 0 when user is guest`() = runTest {
        val page = 1
        val user = UserType.GuestUser(
            sessionId = "123",
            expiredAt = "2023-12-31T23:59:59Z"
        )

        coEvery { userRepository.getUser() } returns user
        coEvery { movieRepository.getRatedMovies(0, page) } returns emptyList()

        val result = useCase(page)

        assertEquals(0, result.size)
        coVerify { movieRepository.getRatedMovies(0, page) }
    }

    @Test
    fun `invoke should fallback to 0 when userId is malformed`() = runTest {
        val page = 1
        val user = UserType.AuthenticatedUser(
            id = "not_a_number",
            name = "Ahmed",
            username = "Ahmed",
            sessionId = "123",
            image = null,
            recentlyCollectionId = 1
        )

        coEvery { userRepository.getUser() } returns user
        coEvery { movieRepository.getRatedMovies(0, page) } returns emptyList()

        val result = useCase(page)

        assertEquals(0, result.size)
        coVerify { movieRepository.getRatedMovies(0, page) }
    }
}