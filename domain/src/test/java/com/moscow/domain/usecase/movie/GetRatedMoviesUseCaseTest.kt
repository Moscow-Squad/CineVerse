package com.moscow.domain.usecase.movie

import com.google.common.truth.Truth.assertThat
import com.moscow.domain.model.Movie
import com.moscow.domain.model.UserType
import com.moscow.domain.repository.MovieRepository
import com.moscow.domain.repository.auth.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetRatedMoviesUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var userRepository: UserRepository
    private lateinit var getRatedMoviesUseCase: GetRatedMoviesUseCase

    @BeforeEach
    fun setUp() {
        movieRepository = mockk(relaxed = true)
        userRepository = mockk(relaxed = true)
        getRatedMoviesUseCase = GetRatedMoviesUseCase(movieRepository, userRepository)
    }

    @Test
    fun `getRatedMoviesUseCase should call repository method`() = runTest {
        // Given
        val page = 1
        coEvery { userRepository.getUser() } returns UserType.AuthenticatedUser(id = "177", sessionId = "456", recentlyCollectionId = 789, image = null, name = "John Doe", username = "johndoe")

        // When
        getRatedMoviesUseCase(page)

        // Then
        coVerify(exactly = 1) { movieRepository.getRatedMovies(177, page) }
    }

    @Test
    fun `getRatedMoviesUseCase should return result from repository`() = runTest {
        // Given
        val page = 1
        val expectedResult = listOf<GetRatedMoviesUseCase.RatedMovieResult>(mockk(), mockk())
        coEvery { userRepository.getUser() } returns UserType.AuthenticatedUser(id = "27", sessionId = "657", recentlyCollectionId = 789, image = null, name = "John Doe", username = "johndoe")
        coEvery { movieRepository.getRatedMovies(27, page) } returns expectedResult

        // When
        val result = getRatedMoviesUseCase(page)

        // Then
        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { movieRepository.getRatedMovies(27, page) }
    }

    @Test
    fun `getRatedMoviesUseCase should complete operation successfully`() = runTest {
        // Given
        val page = 2
        coEvery { userRepository.getUser() } returns UserType.AuthenticatedUser(id = "87", sessionId = "66", recentlyCollectionId = 789, image = null, name = "John Doe", username = "johndoe")

        // When
        val result = getRatedMoviesUseCase(page)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `getRatedMoviesUseCase should handle multiple invocations`() = runTest {
        // Given
        val page = 1
        coEvery { userRepository.getUser() } returns UserType.AuthenticatedUser(id = "986", sessionId = "367", recentlyCollectionId = 789, image = null, name = "John Doe", username = "johndoe")

        // When
        repeat(3) { getRatedMoviesUseCase(page) }

        // Then
        coVerify(exactly = 3) { movieRepository.getRatedMovies(986, page) }
    }

    @Test
    fun `getRatedMoviesUseCase makes exactly one repository call`() = runTest {
        // Given
        val page = 3
        coEvery { userRepository.getUser() } returns UserType.AuthenticatedUser(id = "202", sessionId = "45", recentlyCollectionId = 789, image = null, name = "John Doe", username = "johndoe")

        // When
        getRatedMoviesUseCase(page)

        // Then
        coVerify(exactly = 1) { movieRepository.getRatedMovies(202, page) }
        confirmVerified(movieRepository)
    }

    @Test
    fun `getRatedMoviesUseCase respects number of calls`() = runTest {
        // Given
        val page = 2
        coEvery { userRepository.getUser() } returns UserType.AuthenticatedUser(id = "303", sessionId = "39", recentlyCollectionId = 789, image = null, name = "John Doe", username = "johndoe")

        // When
        repeat(2) { getRatedMoviesUseCase(page) }

        // Then
        coVerify(exactly = 2) { movieRepository.getRatedMovies(303, page) }
    }
}