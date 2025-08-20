package com.moscow.repository

import com.google.common.truth.Truth.assertThat
import com.moscow.data_source.remote.GenreRemoteDataSource
import com.moscow.mapper.toDomain
import com.moscow.remote.dto.genre.GenreDto
import com.moscow.remote.dto.genre.GenreResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GenreRepositoryImplTest {

    private val genreRemoteDataSource = mockk<GenreRemoteDataSource>()
    private lateinit var repository: GenreRepositoryImpl

    @BeforeEach
    fun setup() {
        repository = GenreRepositoryImpl(genreRemoteDataSource)
    }

    @Test
    fun `getSeriesGenres returns mapped genres`() = runTest {
        val genreDto1 = GenreDto(id = 1, name = "Action")
        val genreDto2 = GenreDto(id = 2, name = "Comedy")
        val mockResponse = GenreResponse(genres = listOf(genreDto1, genreDto2))

        coEvery { genreRemoteDataSource.getSeriesGenres() } returns mockResponse

        val result = repository.getSeriesGenres()

        assertThat(result).hasSize(2)
        assertThat(result[0]).isEqualTo(genreDto1.toDomain())
        assertThat(result[1]).isEqualTo(genreDto2.toDomain())
        coVerify(exactly = 1) { genreRemoteDataSource.getSeriesGenres() }
    }

    @Test
    fun `getMoviesGenres returns mapped genres`() = runTest {
        val genreDto1 = GenreDto(id = 10, name = "Drama")
        val genreDto2 = GenreDto(id = 20, name = "Thriller")
        val mockResponse = GenreResponse(genres = listOf(genreDto1, genreDto2))

        coEvery { genreRemoteDataSource.getMoviesGenres() } returns mockResponse

        val result = repository.getMoviesGenres()

        assertThat(result).hasSize(2)
        assertThat(result[0]).isEqualTo(genreDto1.toDomain())
        assertThat(result[1]).isEqualTo(genreDto2.toDomain())
        coVerify(exactly = 1) { genreRemoteDataSource.getMoviesGenres() }
    }

    @Test
    fun `both methods handle empty genre lists successfully`() = runTest {
        val emptyResponse = GenreResponse(genres = emptyList())

        coEvery { genreRemoteDataSource.getSeriesGenres() } returns emptyResponse
        coEvery { genreRemoteDataSource.getMoviesGenres() } returns emptyResponse

        val seriesGenres = repository.getSeriesGenres()
        val moviesGenres = repository.getMoviesGenres()

        assertThat(seriesGenres).isEmpty()
        assertThat(moviesGenres).isEmpty()
        coVerify(exactly = 1) { genreRemoteDataSource.getSeriesGenres() }
        coVerify(exactly = 1) { genreRemoteDataSource.getMoviesGenres() }
    }
}