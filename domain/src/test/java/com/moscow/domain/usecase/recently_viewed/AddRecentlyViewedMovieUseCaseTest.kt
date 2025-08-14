package com.moscow.domain.usecase.recently_viewed

import com.google.common.truth.Truth.assertThat
import com.moscow.domain.model.Movie
import com.moscow.domain.repository.RecentlyViewedRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddRecentlyViewedMovieUseCaseTest {

    private lateinit var repository: RecentlyViewedRepository
    private lateinit var addRecentlyViewedMovieUseCase: AddRecentlyViewedMovieUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        addRecentlyViewedMovieUseCase = AddRecentlyViewedMovieUseCase(repository)
    }

    @Test
    fun `addRecentlyViewedMovieUseCase should call repository method`() = runTest {
        // Given
        val movie = mockk<Movie>()

        // When
        addRecentlyViewedMovieUseCase(movie)

        // Then
        coVerify(exactly = 1) { repository.addRecentlyViewedMovie(movie = movie) }
    }

    @Test
    fun `addRecentlyViewedMovieUseCase should return result from repository`() = runTest {
        // Given
        val movie = mockk<Movie>()
        coEvery { repository.addRecentlyViewedMovie(movie) } returns Unit

        // When
        val result = addRecentlyViewedMovieUseCase(movie)

        // Then
        assertThat(result).isNotNull() // Unit is non-null
        coVerify(exactly = 1) { repository.addRecentlyViewedMovie(movie) }
    }

    @Test
    fun `addRecentlyViewedMovieUseCase should complete operation successfully`() = runTest {
        // Given
        val movie = mockk<Movie>()
        coEvery { repository.addRecentlyViewedMovie(any()) } returns Unit

        // When
        val result = addRecentlyViewedMovieUseCase(movie)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `addRecentlyViewedMovieUseCase should handle multiple invocations`() = runTest {
        // Given
        val movie = mockk<Movie>()

        // When
        repeat(3) { addRecentlyViewedMovieUseCase(movie) }

        // Then
        coVerify(exactly = 3) { repository.addRecentlyViewedMovie(movie) }
    }

    @Test
    fun `addRecentlyViewedMovieUseCase makes exactly one repository call`() = runTest {
        // Given
        val movie = mockk<Movie>()

        // When
        addRecentlyViewedMovieUseCase(movie)

        // Then
        coVerify(exactly = 1) { repository.addRecentlyViewedMovie(movie) }
        confirmVerified(repository)
    }

    @Test
    fun `addRecentlyViewedMovieUseCase respects number of calls`() = runTest {
        // Given
        val movie = mockk<Movie>()

        // When
        repeat(2) { addRecentlyViewedMovieUseCase(movie) }

        // Then
        coVerify(exactly = 2) { repository.addRecentlyViewedMovie(movie) }
    }
}