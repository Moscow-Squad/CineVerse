package com.moscow.data.remote.data_source

import com.google.common.truth.Truth
import com.moscow.data_source.remote.GenreRemoteDataSource
import com.moscow.remote.data_source.GenreRemoteDataSourceImpl
import com.moscow.remote.dto.GenreDto
import com.moscow.remote.dto.GenreResponse
import com.moscow.remote.services.GenreService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class GenreRemoteDataSourceImplTest {

    private lateinit var genreService: GenreService
    private lateinit var genreRemoteDataSource: GenreRemoteDataSource

    @Before
    fun setup(){
        genreService = mockk()
        genreRemoteDataSource = GenreRemoteDataSourceImpl(genreService)
    }

    @Test
    fun`should return the list of genres for movies when call getMoviesGenres`() = runTest {
        val expected = GenreResponse(listOf(GenreDto(1, "Action")))
        coEvery { genreService.getMoviesGenres() } returns Response.success(expected)

        val result = genreRemoteDataSource.getMoviesGenres()

        Truth.assertThat(expected).isEqualTo(result)
    }

    @Test
    fun`should return the list of genres for series when call getSeriesGenres`() = runTest {
        val expected = GenreResponse(listOf(GenreDto(2, "Drama")))
        coEvery { genreService.getSeriesGenres() } returns Response.success(expected)

        val result = genreRemoteDataSource.getSeriesGenres()

        Truth.assertThat(expected).isEqualTo(result)
    }
}