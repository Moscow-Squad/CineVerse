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

class GetMovieByGenreIdUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var getMovieByGenreIdUseCase: GetMovieByGenreIdUseCase

    @BeforeEach
    fun setUp() {
        movieRepository = mockk(relaxed = true)
        getMovieByGenreIdUseCase = GetMovieByGenreIdUseCase(movieRepository)
    }

    @Test
    fun `getMovieByGenreIdUseCase should call repository method`() = runTest {
        // Given
        val genreId = 1
        val page = 1

        // When
        getMovieByGenreIdUseCase(genreId, page)

        // Then
        coVerify(exactly = 1) { movieRepository.getMoviesByGenreId(genreId = genreId, page = page) }
    }

    @Test
    fun `getMovieByGenreIdUseCase should return result from repository`() = runTest {
        // Given
        val genreId = 2
        val page = 1
        val movie1 = mockk<Movie> { coEvery { id } returns 1 }
        val movie2 = mockk<Movie> { coEvery { id } returns 2 }
        val repositoryMovies = listOf(movie1, movie2)
        coEvery { movieRepository.getMoviesByGenreId(genreId = genreId, page = page) } returns repositoryMovies

        // When
        val result = getMovieByGenreIdUseCase(genreId, page)

        // Then
        assertThat(result).isEqualTo(repositoryMovies)
        coVerify(exactly = 1) { movieRepository.getMoviesByGenreId(genreId = genreId, page = page) }
    }

    @Test
    fun `getMovieByGenreIdUseCase should complete operation successfully`() = runTest {
        // Given
        val genreId = 3
        val page = 2

        // When
        val result = getMovieByGenreIdUseCase(genreId, page)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `getMovieByGenreIdUseCase should handle multiple invocations`() = runTest {
        // Given
        val genreId = 4
        val page = 1

        // When
        repeat(3) { getMovieByGenreIdUseCase(genreId, page) }

        // Then
        coVerify(exactly = 3) { movieRepository.getMoviesByGenreId(genreId = genreId, page = page) }
    }

    @Test
    fun `getMovieByGenreIdUseCase makes exactly one repository call`() = runTest {
        // Given
        val genreId = 5
        val page = 3

        // When
        getMovieByGenreIdUseCase(genreId, page)

        // Then
        coVerify(exactly = 1) { movieRepository.getMoviesByGenreId(genreId = genreId, page = page) }
        confirmVerified(movieRepository)
    }

    @Test
    fun `getMovieByGenreIdUseCase respects number of calls`() = runTest {
        // Given
        val genreId = 6
        val page = 2

        // When
        repeat(2) { getMovieByGenreIdUseCase(genreId, page) }

        // Then
        coVerify(exactly = 2) { movieRepository.getMoviesByGenreId(genreId = genreId, page = page) }
    }
}