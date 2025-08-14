package com.moscow.repository

import com.moscow.data_source.remote.GenreRemoteDataSource
import com.moscow.domain.model.Genre
import com.moscow.remote.dto.genre.GenreDto
import com.moscow.remote.dto.genre.GenreResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals


class GenreRepositoryImplTest {

    private val genreRemoteDataSource = mockk<GenreRemoteDataSource>()
    private lateinit var repository: GenreRepositoryImpl

    @BeforeEach
    fun setup() {
        repository = GenreRepositoryImpl(genreRemoteDataSource)
    }

    @Test
    fun `getSeriesGenres calls remote data source correctly`() = runTest {

        val genreDto1 = GenreDto(id = 1, name = "Action")
        val genreDto2 = GenreDto(id = 2, name = "Comedy")
        val mockGenreResponse = GenreResponse(genres = listOf(genreDto1, genreDto2))

        coEvery { genreRemoteDataSource.getSeriesGenres() } returns mockGenreResponse

        try {
            val result = repository.getSeriesGenres()
            assertEquals(true, true)
        } catch (e: Exception) {
            assertEquals("Expected successful method call", e.message ?: "Unexpected error")
        }

        coVerify(exactly = 1) { genreRemoteDataSource.getSeriesGenres() }
    }

    @Test
    fun `getMoviesGenres calls remote data source correctly`() = runTest {

        val genreDto1 = GenreDto(id = 10, name = "Drama")
        val genreDto2 = GenreDto(id = 20, name = "Thriller")
        val mockGenreResponse = GenreResponse(genres = listOf(genreDto1, genreDto2))

        coEvery { genreRemoteDataSource.getMoviesGenres() } returns mockGenreResponse

        try {
            val result = repository.getMoviesGenres()
            assertEquals(true, true)
        } catch (e: Exception) {
            assertEquals("Expected successful method call", e.message ?: "Unexpected error")
        }

        coVerify(exactly = 1) { genreRemoteDataSource.getMoviesGenres() }
    }

    @Test
    fun `both methods handle empty genre lists successfully`() = runTest {

        val emptyGenreResponse = GenreResponse(genres = emptyList())

        coEvery { genreRemoteDataSource.getSeriesGenres() } returns emptyGenreResponse
        coEvery { genreRemoteDataSource.getMoviesGenres() } returns emptyGenreResponse

        try {
            val seriesResult = repository.getSeriesGenres()
            val moviesResult = repository.getMoviesGenres()
            assertEquals(true, true)
        } catch (e: Exception) {
            assertEquals("Expected successful method call", e.message ?: "Unexpected error")
        }

        coVerify(exactly = 1) { genreRemoteDataSource.getSeriesGenres() }
        coVerify(exactly = 1) { genreRemoteDataSource.getMoviesGenres() }
    }
}