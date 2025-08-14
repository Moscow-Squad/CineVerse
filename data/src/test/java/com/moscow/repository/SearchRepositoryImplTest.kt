package com.moscow.repository

import com.moscow.data_source.local.SearchLocalDataSource
import com.moscow.data_source.remote.SearchRemoteDataSource
import com.moscow.domain.model.Actor
import com.moscow.domain.model.Movie
import com.moscow.local.entity.FavouriteGenreEntity
import com.moscow.mapper.toDomain
import com.moscow.mapper.toModel
import com.moscow.remote.dto.actor.ActorDto
import com.moscow.remote.dto.media_item.movie.MovieDto
import com.moscow.remote.dto.media_item.series.SeriesDto
import com.moscow.remote.dto.suggestion.SuggestionDto
import com.moscow.utils.ApiResponse
import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SearchRepositoryImplTest {

    private val searchLocalDataSource: SearchLocalDataSource = mockk(relaxed = true)
    private val searchRemoteDataSource: SearchRemoteDataSource = mockk(relaxed = true)

    private lateinit var repository: SearchRepositoryImpl

    @BeforeEach
    fun setup() {
        repository = SearchRepositoryImpl(
            searchLocalDataSource = searchLocalDataSource,
            searchRemoteDataSource = searchRemoteDataSource
        )
    }

    @Test
    fun `getLocalSuggestions should return search history from local data source`() = runTest {

        val expected = listOf("batman", "spider-man")
        coEvery { searchLocalDataSource.getAllSearchHistory() } returns flowOf(expected)

        val result = repository.getLocalSuggestions().first()

        assertEquals(expected, result)
        coVerify(exactly = 1) { searchLocalDataSource.getAllSearchHistory() }
    }

    @Test
    fun `deleteSearchHistory should call local data source with correct search term`() = runTest {

        val term = "batman"
        coEvery { searchLocalDataSource.deleteSearchHistory(term) } returns Unit

        val result = repository.deleteSearchHistory(term)

        assertEquals(Unit, result)
        coVerify(exactly = 1) { searchLocalDataSource.deleteSearchHistory(term) }
    }

    @Test
    fun `clearSearchHistory should call deleteAllSearchHistory on local`() = runTest {
        coEvery { searchLocalDataSource.deleteAllSearchHistory() } returns Unit

        val result = repository.clearSearchHistory()

        assertEquals(Unit, result)
        coVerify(exactly = 1) { searchLocalDataSource.deleteAllSearchHistory() }
    }

    @Test
    fun `cacheSearchQuery should call insertSearchHistory on local`() = runTest {
        val query = "joker"
        coEvery { searchLocalDataSource.insertSearchHistory(query) } returns Unit

        val result = repository.cacheSearchQuery(query)

        assertEquals(Unit, result)
        coVerify(exactly = 1) { searchLocalDataSource.insertSearchHistory(query) }
    }

    @Test
    fun `getRemoteSuggestions should return mapped suggestions from remote`() = runTest {
        val page = 1
        val keyword = "bat"
        val s1 = mockk<SuggestionDto>()
        val s2 = mockk<SuggestionDto>()
        every { s1.toModel() } returns "batman"
        every { s2.toModel() } returns "batwoman"

        val api = ApiResponse(results = listOf(s1, s2), page = page, totalPages = 1, totalResults = 2)
        coEvery { searchRemoteDataSource.searchByKeyword(keyword, page, false) } returns api

        val result = repository.getRemoteSuggestions(keyword, page)

        assertEquals(listOf("batman", "batwoman"), result)
        coVerify(exactly = 1) { searchRemoteDataSource.searchByKeyword(keyword, page, false) }
    }

    @Test
    fun `searchMovie should return empty list when remote results are null`() = runTest {
        val query = "nothing"
        val page = 1
        val api = ApiResponse<MovieDto>(results = null, page = page, totalPages = 1, totalResults = 0)
        coEvery { searchRemoteDataSource.searchMovie(query, page, false) } returns api
        coEvery { searchLocalDataSource.getFavouriteGenres() } returns flowOf(emptyList())

        val result = repository.searchMovie(query, page).first()

        assertEquals(0, result.size)
    }


    private fun createMovie(id: Int, title: String) = Movie(
        id = id,
        title = title,
        overview = "overview",
        trailerUrl = "trailer",
        backdropUrl = "back",
        posterUrl = "poster",
        releaseDate = null,
        genreIds = emptyList(),
        genres = emptyList(),
        duration = Movie.Duration(hours = 0, minutes = 0),
        rating = 8.5f
    )
}