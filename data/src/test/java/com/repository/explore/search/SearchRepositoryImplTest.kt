package com.repository.explore.search

import androidx.work.WorkManager
import com.android.domain.exception.CineVerseException.NotFoundCineVerseException
import com.google.common.truth.Truth.assertThat
import com.local.entity.ActorEntity
import com.local.entity.MovieEntity
import com.local.entity.SeriesEntity
import com.mapper.toDomain
import com.mapper.toModel
import com.remote.dto.ActorDto
import com.remote.dto.MovieDto
import com.remote.dto.MultiSearchDto
import com.remote.dto.SeriesDto
import com.remote.dto.SuggestionDto
import com.remote.source.SearchRemoteDataSource
import com.repository.mapper.toDomain
import com.repository.mapper.toEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Disabled

@ExperimentalCoroutinesApi
class SearchRepositoryImplTest {
    private lateinit var searchLocalDataSource: SearchLocalDateSource
    private lateinit var searchRemoteDataSource: SearchRemoteDataSource
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var repository: SearchRepositoryImpl
    private lateinit var workManager: WorkManager

    @Before
    fun setUp() {
        searchLocalDataSource = mockk()
        searchRemoteDataSource = mockk()
        workManager = mockk()
        testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        repository = SearchRepositoryImpl(
            searchLocalDataSource,
            searchRemoteDataSource,
            testDispatcher,
            workManager
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getLocalMoviesBySearchTerm should return mapped movies when local movies is valid`() =
        runTest {
            // Given
            val searchTerm = AVENGERS
            val movieEntities = listOf(
                MovieEntity(
                    id = 101L,
                    searchTerm = searchTerm,
                    name = AVENGERS,
                    rating = 7.5f,
                    genresId = listOf(28, 12),
                    releaseDate = "2023-01-01",
                    poster = "/poster.jpg",
                    duration = "2h 23m"
                )
            )
            val expectedMovies = movieEntities.toDomain()
            coEvery { searchLocalDataSource.getMoviesBySearchTerm(searchTerm) } returns movieEntities

            // When
            val result = repository.getLocalMoviesBySearchTerm(searchTerm)

            // Then
            assertThat(result).isEqualTo(expectedMovies)
        }

    @Test
    fun `getLocalMoviesBySearchTerm should return empty list when local movies is empty`() =
        runTest {
            // Given
            val searchTerm = AVENGERS
            coEvery { searchLocalDataSource.getMoviesBySearchTerm(searchTerm) } returns emptyList()

            // When
            val result = repository.getLocalMoviesBySearchTerm(searchTerm)

            // Then
            assertThat(result).isEmpty()
        }

    @Test
    fun `getLocalSuggestions should emit search history when local suggestions is valid`() =
        runTest {
            // Given
            val suggestions = listOf(AVENGERS, BATMAN)
            coEvery { searchLocalDataSource.getAllSearchHistory() } returns flowOf(suggestions)

            // When
            val result = repository.getLocalSuggestions().toList()

            // Then
            assertThat(result).containsExactly(suggestions)
        }

    @Test
    fun `getLocalSuggestions should emit empty list when local suggestions is empty`() =
        runTest {
            // Given
            coEvery { searchLocalDataSource.getAllSearchHistory() } returns flowOf(emptyList())

            // When
            val result = repository.getLocalSuggestions().toList()

            // Then
            assertThat(result).containsExactly(emptyList<String>())
        }

    @Test
    fun `getLocalSuggestions should throw exception when error happen at local suggestions`() =
        runTest {
            // Given
            val exception = RuntimeException("Database error")
            coEvery { searchLocalDataSource.getAllSearchHistory() } throws exception

            // When & Then
            try {
                repository.getLocalSuggestions().toList()
            } catch (e: Exception) {
                assertThat(e).isEqualTo(exception)
            }
        }

    @Test
    fun `deleteSearchHistory should delete search history when valid search term is valid`() =
        runTest {
            // Given
            val searchTerm = AVENGERS
            coEvery { searchLocalDataSource.deleteSearchHistory(searchTerm) } returns Unit

            // When
            repository.deleteSearchHistory(searchTerm)

            // Then
            coVerify { searchLocalDataSource.deleteSearchHistory(searchTerm) }
        }

    @Test
    fun `getRemoteSuggestions should emit mapped suggestions when remote suggestions is valid`() =
        runTest {
            // Given
            val keyword = AVENGERS
            val page = 1
            val suggestionDtos = listOf(
                SuggestionDto(id = 1, name = AVENGERS),
                SuggestionDto(id = 2, name = BATMAN)
            )
            val expectedSuggestions = suggestionDtos.map { it.toModel() }
            coEvery { searchRemoteDataSource.getSuggestions(keyword, page) } returns suggestionDtos

            // When
            val result = repository.getRemoteSuggestions(keyword, page).toList()

            // Then
            assertThat(result).containsExactly(expectedSuggestions)
        }

    @Test
    fun `getRemoteSuggestions should emit empty list when remote suggestions is empty`() =
        runTest {
            // Given
            val keyword = AVENGERS
            val page = 1
            coEvery { searchRemoteDataSource.getSuggestions(keyword, page) } returns emptyList()

            // When
            val result = repository.getRemoteSuggestions(keyword, page).toList()

            // Then
            assertThat(result).containsExactly(emptyList<String>())
        }

    @Test
    fun `searchMulti should return mapped results when remote multi-search is valid`() =
        runTest {
            // Given
            val query = AVENGERS
            val multiSearchDtos = MULTI_SEARCH_DTOs
            val expectedMultiSearch =
                multiSearchDtos.map { it.toDomain() }
            coEvery { searchRemoteDataSource.searchMulti(query) } returns multiSearchDtos

            // When
            val result = repository.searchMulti(query).toList()

            // Then
            assertThat(result).containsExactly(expectedMultiSearch)
        }

    @Test
    fun `searchMulti should throw NotFoundCineVerseException when remote multi-search is empty`() =
        runTest {
            // Given
            val query = AVENGERS
            coEvery { searchRemoteDataSource.searchMulti(query) } returns emptyList()

            // When & Then
            try {
                repository.searchMulti(query).toList()
            } catch (e: Exception) {
                assertThat(e).isEqualTo(NotFoundCineVerseException)
            }
        }

    @Disabled
    fun `Given valid local movies and isHistory true, When searchMovie is called, Then emits local movies`() =
        runTest {
            // Given
            val query = AVENGERS
            val isHistory = true
            val movieEntities = listOf(
                MovieEntity(
                    id = 101L,
                    searchTerm = query,
                    name = AVENGERS,
                    rating = 7.5f,
                    genresId = listOf(28, 12),
                    releaseDate = "2023-01-01",
                    poster = "/poster.jpg",
                    duration = "2h 23m"
                )
            )
            val expectedMovies = movieEntities.toDomain()
            coEvery { searchLocalDataSource.getMoviesBySearchTerm(query) } returns movieEntities
            coEvery { searchRemoteDataSource.searchMovie(query) } returns emptyList()

            // When
            val result = repository.searchMovie(query, isHistory).toList()

            // Then
            assertThat(result).containsExactly(expectedMovies)
        }

    @Disabled
    fun `Given valid remote movies and isHistory false, When searchMovie is called, Then emits remote movies`() =
        runTest {
            // Given
            val query = AVENGERS
            val isHistory = false
            val movieDtos = listOf(
                MovieDto(
                    id = 101,
                    title = AVENGERS,
                    originalTitle = AVENGERS,
                    overview = "A superhero movie",
                    posterPath = "/poster.jpg",
                    backdropPath = "/backdrop.jpg",
                    adult = false,
                    releaseDate = "2023-01-01",
                    genreIds = listOf(28, 12),
                    originalLanguage = "en",
                    voteAverage = 7.5f,
                    video = false,
                    popularity = 100.0,
                    voteCount = 1000,
                    mediaType = "Movie",
                    firstAirDate = null
                )
            )
            val expectedMovies = movieDtos.map { it.toDomain() }
            coEvery { searchRemoteDataSource.searchMovie(query) } returns movieDtos
            coEvery { searchLocalDataSource.insertSearchHistory(query) } returns Unit
            coEvery { expectedMovies.toEntity(query) } returns emptyList()
            coEvery { searchLocalDataSource.insertMovie(any(), query) } returns Unit

            // When
            val result = repository.searchMovie(query, isHistory).toList()

            // Then
            assertThat(result).containsExactly(expectedMovies)
        }

    @Test
    fun `searchMovie should throw NotFoundCineVerseException when remote movies is empty and there is no history`() =
        runTest {
            // Given
            val query = AVENGERS
            val isHistory = false
            coEvery { searchRemoteDataSource.searchMovie(query) } returns emptyList()

            // When & Then
            try {
                repository.searchMovie(query, isHistory).toList()
            } catch (e: Exception) {
                assertThat(e).isEqualTo(NotFoundCineVerseException)
            }
        }

    @Disabled
    fun `Given valid local series and isHistory true, When searchSeries is called, Then emits local series`() =
        runTest {
            // Given
            val query = "Stranger"
            val isHistory = true
            val seriesEntities = listOf(
                SeriesEntity(
                    id = 201,
                    searchTerm = query,
                    name = "Stranger Things",
                    genresId = listOf(10765, 18),
                    description = "A sci-fi series",
                    rating = 8.0f,
                    releaseDate = LocalDate.parse("2023-03-01"),
                    poster = "/poster.jpg"
                )
            )
            val expectedSeries = seriesEntities.toDomain()
            coEvery { searchLocalDataSource.getSeriesBySearchTerm(query) } returns seriesEntities
            coEvery { searchRemoteDataSource.searchSeries(query) } returns emptyList()

            // When
            val result = repository.searchSeries(query, isHistory).toList()

            // Then
            assertThat(result).containsExactly(expectedSeries)
        }

    @Disabled
    fun `Given valid remote series and isHistory false, When searchSeries is called, Then emits remote series`() =
        runTest {
            // Given
            val query = "Stranger"
            val isHistory = false
            val seriesDtos = listOf(
                SeriesDto(
                    id = 201,
                    name = "Stranger Things",
                    overview = "A sci-fi series",
                    posterPath = "/poster.jpg",
                    backdropPath = "/backdrop.jpg",
                    adult = false,
                    firstAirDate = "2023-03-01",
                    genreIds = listOf(10765, 18),
                    originalLanguage = "en",
                    originCountry = listOf("US"),
                    originalName = "Stranger Things",
                    voteAverage = 8.0f,
                    popularity = 300.0,
                    voteCount = 2000
                )
            )
            val expectedSeries = seriesDtos.map { it.toDomain() } // Directly use toDomain()
            coEvery { searchRemoteDataSource.searchSeries(query) } returns seriesDtos
            // Mock insertSeries calls to satisfy execution (not tested due to WorkManager)
            coEvery { searchLocalDataSource.insertSearchHistory(query) } returns Unit
            coEvery { expectedSeries.toEntity(query) } returns emptyList()
            coEvery { searchLocalDataSource.insertSeries(any(), query) } returns Unit

            // When
            val result = repository.searchSeries(query, isHistory).toList()

            // Then
            assertThat(result).containsExactly(expectedSeries)
        }

    @Test
    fun `searchSeries should throw NotFoundCineVerseException when remote series is empty and there is no history`() =
        runTest {
            // Given
            val query = "Stranger"
            val isHistory = false
            coEvery { searchRemoteDataSource.searchSeries(query) } returns emptyList()

            // When & Then
            try {
                repository.searchSeries(query, isHistory).toList()
            } catch (e: Exception) {
                assertThat(e).isEqualTo(NotFoundCineVerseException)
            }
        }

    @Disabled
    fun `Given valid local actors and isHistory true, When searchActor is called, Then emits local actors`() =
        runTest {
            // Given
            val query = ROBERT
            val isHistory = true
            val actorEntities = listOf(
                ActorEntity(
                    id = 301,
                    searchTerm = query,
                    name = "Robert Downey Jr.",
                    gender = com.local.entity.Gender.MALE,
                    profileImg = "/profile.jpg"
                )
            )
            val expectedActors = actorEntities.toDomain()
            coEvery { searchLocalDataSource.getActorsBySearchTerm(query) } returns actorEntities
            coEvery { searchRemoteDataSource.searchPearson(query) } returns emptyList()

            // When
            val result = repository.searchActor(query, isHistory).toList()

            // Then
            assertThat(result).containsExactly(expectedActors)
        }

    @Disabled
    fun `Given valid remote actors and isHistory false, When searchActor is called, Then emits remote actors`() =
        runTest {
            // Given
            val query = ROBERT
            val isHistory = false
            val actorDtos = listOf(
                ActorDto(
                    id = 301,
                    name = "Robert Downey Jr.",
                    gender = 0,
                    profilePath = "/profile.jpg"
                )
            )
            val expectedActors = actorDtos.map { it.toDomain() }
            coEvery { searchRemoteDataSource.searchPearson(query) } returns actorDtos
            coEvery { searchLocalDataSource.insertSearchHistory(query) } returns Unit
            coEvery { expectedActors.toEntity(query) } returns emptyList()
            coEvery { searchLocalDataSource.insertActors(any(), query) } returns Unit

            // When
            val result = repository.searchActor(query, isHistory).toList()

            // Then
            assertThat(result).containsExactly(expectedActors)
        }

    @Test
    fun `searchActor should throw NotFoundCineVerseException when remote actors is empty and there is no history`() =
        runTest {
            // Given
            val query = ROBERT
            val isHistory = false
            coEvery { searchRemoteDataSource.searchPearson(query) } returns emptyList()

            // When & Then
            try {
                repository.searchActor(query, isHistory).toList()
            } catch (e: Exception) {
                assertThat(e).isEqualTo(NotFoundCineVerseException)
            }
        }

    private companion object {

        const val AVENGERS = "Avengers"
        const val BATMAN = "Batman"
        const val ROBERT = "Robert"

        val MULTI_SEARCH_DTOs = listOf(
            MultiSearchDto(
                id = 101,
                name = AVENGERS,
                mediaType = "Movie",
                overview = "A superhero movie",
                posterPath = "/poster.jpg",
                adult = false,
                backdropPath = "/backdrop.jpg",
                firstAirDate = "",
                genreIds = listOf(28, 12),
                originalName = AVENGERS,
                popularity = 100.0,
                voteAverage = 7.5f
            ),
            MultiSearchDto(
                id = 201,
                name = "Avengers: The Series",
                mediaType = "Tv",
                overview = "A superhero series",
                posterPath = "/series_poster.jpg",
                adult = false,
                backdropPath = "/series_backdrop.jpg",
                firstAirDate = "2023-03-01",
                genreIds = listOf(10765, 18),
                originalName = "Avengers: The Series",
                popularity = 200.0,
                voteAverage = 8.0f
            )
        )
    }
}