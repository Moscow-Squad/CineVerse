package com.moscow.data.remote.data_source

import com.android.domain.exception.CineVerseException.IOException
import com.google.common.truth.Truth.assertThat
import com.moscow.data_source.remote.SearchRemoteDataSource
import com.moscow.remote.data_source.SearchRemoteDataSourceImpl
import com.moscow.remote.dto.MovieDto
import com.moscow.remote.dto.actor.ActorDto
import com.moscow.remote.dto.series.SeriesDto
import com.moscow.remote.dto.suggestion.SuggestionDto
import com.moscow.remote.services.SearchService
import com.moscow.utils.ApiResponse
import com.moscow.utils.CineVerseExceptions
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import retrofit2.Response
import kotlin.test.Test

class SearchRemoteDataSourceImplTest {

    private lateinit var searchService: SearchService
    private lateinit var searchRemoteDataSource: SearchRemoteDataSource

    @BeforeEach
    fun setup() {
        searchService = mockk()
        searchRemoteDataSource = SearchRemoteDataSourceImpl(searchService)
    }

    /**
     * Search movies
     */
    @Test
    fun `searchMovie should return movie data when API call is successful`() = runTest {
        val query = "Avengers"
        val page = 1
        val includeAdult = false
        val expectedMovie = MovieDto(title = "Avengers: Endgame")
        val successResponse =
            Response.success(ApiResponse<MovieDto>(results = listOf(expectedMovie)))

        coEvery {
            searchService.searchMovie(query, page, includeAdult)
        } returns successResponse

        // When
        val result = searchRemoteDataSource.searchMovie(query, page, includeAdult)
        assertThat(result.results).contains(expectedMovie)
    }


    @Test
    fun `searchMovie should return error when API call fails`() = runTest {
        // Given
        val query = "Avengers"
        val page = 1
        val includeAdult = false

        val errorApiResponse = ApiResponse<MovieDto>(
            statusCode = 404,
            statusMessage = "Resource not found",
            success = false
        )

        val errorResponse = Response.success(errorApiResponse)

        coEvery {
            searchService.searchMovie(query, page, includeAdult)
        } returns errorResponse

        // When
        val result = searchRemoteDataSource.searchMovie(query, page, includeAdult)

        // Then
        assertThat(result.success).isFalse()
        assertThat(result.statusCode).isEqualTo(404)
        assertThat(result.statusMessage).isEqualTo("Resource not found")
        assertThat(result.results).isNull()

    }

    @Test
    fun `searchMovie should handle network exception`() = runTest {
        // Given
        val query = "Avengers"
        val page = 1
        val includeAdult = false

        coEvery {
            searchService.searchMovie(query, page, includeAdult)
        } throws IOException("Network error")

        // When & Then
        assertThrows<CineVerseExceptions> {
            searchRemoteDataSource.searchMovie(query, page, includeAdult)
        }
    }

    /**
     * Search series
     */

    @Test
    fun `searchSeries should return series data when API call is successful`() = runTest {
        val query = "Avengers"
        val page = 1
        val includeAdult = false
        val expectedSeries = SeriesDto(name = "Avengers: Endgame")
        val successResponse =
            Response.success(ApiResponse<SeriesDto>(results = listOf(expectedSeries)))

        coEvery {
            searchService.searchSeries(query, page, includeAdult)
        } returns successResponse

        // When
        val result = searchRemoteDataSource.searchSeries(query, page, includeAdult)
        assertThat(result.results).contains(expectedSeries)
    }


    @Test
    fun `searchSeries should return error when API call fails`() = runTest {
        // Given
        val query = "Avengers"
        val page = 1
        val includeAdult = false

        val errorApiResponse = ApiResponse<SeriesDto>(
            statusCode = 404,
            statusMessage = "Resource not found",
            success = false
        )

        val errorResponse = Response.success(errorApiResponse)

        coEvery {
            searchService.searchSeries(query, page, includeAdult)
        } returns errorResponse

        // When
        val result = searchRemoteDataSource.searchSeries(query, page, includeAdult)

        // Then
        assertThat(result.success).isFalse()
        assertThat(result.statusCode).isEqualTo(404)
        assertThat(result.statusMessage).isEqualTo("Resource not found")
        assertThat(result.results).isNull()

    }

    @Test
    fun `searchSeries should handle network exception`() = runTest {
        // Given
        val query = "Avengers"
        val page = 1
        val includeAdult = false

        coEvery {
            searchService.searchSeries(query, page, includeAdult)
        } throws IOException("Network error")

        // When & Then
        assertThrows<CineVerseExceptions> {
            searchRemoteDataSource.searchSeries(query, page, includeAdult)
        }
    }

    /**
     * Search actors
     */

    @Test
    fun `searchActors should return actor data when API call is successful`() = runTest {
        val query = "Avengers"
        val page = 1
        val includeAdult = false
        val expectedActor = ActorDto(name = "Avengers: Endgame")
        val successResponse =
            Response.success(ApiResponse<ActorDto>(results = listOf(expectedActor)))

        coEvery {
            searchService.searchActor(query, page, includeAdult)
        } returns successResponse

        // When
        val result = searchRemoteDataSource.searchActor(query, page, includeAdult)
        assertThat(result.results).contains(expectedActor)
    }


    @Test
    fun `searchActors should return error when API call fails`() = runTest {
        // Given
        val query = "Avengers"
        val page = 1
        val includeAdult = false

        val errorApiResponse = ApiResponse<ActorDto>(
            statusCode = 404,
            statusMessage = "Resource not found",
            success = false
        )

        val errorResponse = Response.success(errorApiResponse)

        coEvery {
            searchService.searchActor(query, page, includeAdult)
        } returns errorResponse

        // When
        val result = searchRemoteDataSource.searchActor(query, page, includeAdult)

        // Then
        assertThat(result.success).isFalse()
        assertThat(result.statusCode).isEqualTo(404)
        assertThat(result.statusMessage).isEqualTo("Resource not found")
        assertThat(result.results).isNull()

    }

    @Test
    fun `searchActors should handle network exception`() = runTest {
        // Given
        val query = "Avengers"
        val page = 1
        val includeAdult = false

        coEvery {
            searchService.searchActor(query, page, includeAdult)
        } throws IOException("Network error")

        // When & Then
        assertThrows<CineVerseExceptions> {
            searchRemoteDataSource.searchActor(query, page, includeAdult)
        }
    }

    /**
     * get suggestions for keyword
     */

    @Test
    fun `searchByKeyword should return actor data when API call is successful`() = runTest {
        val query = "Avengers"
        val page = 1
        val includeAdult = false
        val expectedActor = SuggestionDto(id = 1, name = "Avengers: Endgame")
        val successResponse =
            Response.success(ApiResponse<SuggestionDto>(results = listOf(expectedActor)))

        coEvery {
            searchService.getSuggestions(query, page, includeAdult)
        } returns successResponse

        // When
        val result = searchRemoteDataSource.searchByKeyword(query, page, includeAdult)
        assertThat(result.results).contains(expectedActor)
    }


    @Test
    fun `searchByKeyword should return error when API call fails`() = runTest {
        // Given
        val query = "Avengers"
        val page = 1
        val includeAdult = false

        val errorApiResponse = ApiResponse<SuggestionDto>(
            statusCode = 404,
            statusMessage = "Resource not found",
            success = false
        )

        val errorResponse = Response.success(errorApiResponse)

        coEvery {
            searchService.getSuggestions(query, page, includeAdult)
        } returns errorResponse

        // When
        val result = searchRemoteDataSource.searchByKeyword(query, page, includeAdult)

        // Then
        assertThat(result.success).isFalse()
        assertThat(result.statusCode).isEqualTo(404)
        assertThat(result.statusMessage).isEqualTo("Resource not found")
        assertThat(result.results).isNull()

    }

    @Test
    fun `searchByKeyword should handle network exception`() = runTest {
        // Given
        val query = "Avengers"
        val page = 1
        val includeAdult = false

        coEvery {
            searchService.getSuggestions(query, page, includeAdult)
        } throws IOException("Network error")

        // When & Then
        assertThrows<CineVerseExceptions> {
            searchRemoteDataSource.searchByKeyword(query, page, includeAdult)
        }
    }

}