package com.moscow.domain.usecase.movie

import com.google.common.truth.Truth.assertThat
import com.moscow.domain.model.Movie
import com.moscow.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetMatchesYourVibesMoviesUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var getMatchesYourVibesMoviesUseCase: GetMatchesYourVibesMoviesUseCase

    @BeforeEach
    fun setUp() {
        movieRepository = mockk(relaxed = true)
        getMatchesYourVibesMoviesUseCase = GetMatchesYourVibesMoviesUseCase(movieRepository)
    }

    @Test
    fun `getMatchesYourVibesMoviesUseCase should call repository method`() = runTest {
        // Given
        val genreId = 1
        val page = 1
        val forceRefresh = false

        // When
        getMatchesYourVibesMoviesUseCase(genreId, page, forceRefresh)

        // Then
        coVerify(exactly = 1) { movieRepository.getMatchYourVibeMovies(genreId = genreId, page = page, forceRefresh = forceRefresh) }
    }

    @Test
    fun `getMatchesYourVibesMoviesUseCase should return result from repository`() = runTest {
        // Given
        val genreId = 2
        val page = 1
        val forceRefresh = true
        val expectedMovies = listOf<Movie>(mockk(), mockk())
        coEvery { movieRepository.getMatchYourVibeMovies(genreId = genreId, page = page, forceRefresh = forceRefresh) } returns expectedMovies

        // When
        val result = getMatchesYourVibesMoviesUseCase(genreId, page, forceRefresh)

        // Then
        assertThat(result).isEqualTo(expectedMovies)
        coVerify(exactly = 1) { movieRepository.getMatchYourVibeMovies(genreId = genreId, page = page, forceRefresh = forceRefresh) }
    }

    @Test
    fun `getMatchesYourVibesMoviesUseCase should complete operation successfully`() = runTest {
        // Given
        val genreId = 3
        val page = 2

        // When
        val result = getMatchesYourVibesMoviesUseCase(genreId, page)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `getMatchesYourVibesMoviesUseCase should handle multiple invocations`() = runTest {
        // Given
        val genreId = 4
        val page = 1
        val forceRefresh = false

        // When
        repeat(3) { getMatchesYourVibesMoviesUseCase(genreId, page, forceRefresh) }

        // Then
        coVerify(exactly = 3) { movieRepository.getMatchYourVibeMovies(genreId = genreId, page = page, forceRefresh = forceRefresh) }
    }

    @Test
    fun `getMatchesYourVibesMoviesUseCase makes exactly one repository call`() = runTest {
        // Given
        val genreId = 5
        val page = 3
        val forceRefresh = true

        // When
        getMatchesYourVibesMoviesUseCase(genreId, page, forceRefresh)

        // Then
        coVerify(exactly = 1) { movieRepository.getMatchYourVibeMovies(genreId = genreId, page = page, forceRefresh = forceRefresh) }
        confirmVerified(movieRepository)
    }

    @Test
    fun `getMatchesYourVibesMoviesUseCase respects number of calls`() = runTest {
        // Given
        val genreId = 6
        val page = 2
        val forceRefresh = false

        // When
        repeat(2) { getMatchesYourVibesMoviesUseCase(genreId, page, forceRefresh) }

        // Then
        coVerify(exactly = 2) { movieRepository.getMatchYourVibeMovies(genreId = genreId, page = page, forceRefresh = forceRefresh) }
    }
}