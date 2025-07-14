package com.repository.explore

import com.android.domain.model.Genre
import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.google.common.truth.Truth.assertThat
import com.remote.dto.GenreDto
import com.remote.dto.MovieDto
import com.remote.dto.SeriesDto
import com.remote.source.ExploreRemoteDataSource
import com.utils.IMAGES_URL
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.After
import org.junit.Before
import kotlin.test.Test

@ExperimentalCoroutinesApi
class ExploreRepositoryImplTest {

    private lateinit var exploreRemoteDataSource: ExploreRemoteDataSource
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var repository: ExploreRepositoryImpl

    @Before
    fun setUp() {
        exploreRemoteDataSource = mockk(relaxed = true)
        testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        repository = ExploreRepositoryImpl(exploreRemoteDataSource, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getSeriesGenres should emit list of mapped genres when series genres is valid`() =
        runTest {
            // Given
            val genreDtos = GENRE_DTOs
            val expectedGenres = EXPECTED_GENRES
            coEvery { exploreRemoteDataSource.getSeriesGenres() } returns genreDtos

            // When
            val result = repository.getSeriesGenres().toList()

            // Then
            assertThat(result).containsExactly(expectedGenres)
        }

    @Test
    fun `getMoviesByGenreId should emit list of mapped movies when get valid movies by genre id`() =
        runTest {
            // Given
            val genreId = 1
            val movieDtos = MOVIE_DTOs
            val expectedMovies = EXPECTED_MOVIES
            coEvery { exploreRemoteDataSource.getMoviesByGenreId(genreId) } returns movieDtos

            // When
            val result = repository.getMoviesByGenreId(genreId).toList()

            // Then
            assertThat(result).containsExactly(expectedMovies)
        }

    @Test
    fun `getMoviesGenres should emit list of mapped genres When get valid movie genres`() =
        runTest {
            // Given
            val genreDtos = GENRE_DTOs
            val expectedGenres = EXPECTED_GENRES
            coEvery { exploreRemoteDataSource.getMoviesGenres() } returns genreDtos

            // When
            val result = repository.getMoviesGenres().toList()

            // Then
            assertThat(result).containsExactly(expectedGenres)
        }

    @Test
    fun `getMovies should emit list of mapped movies when get valid movies`() =
        runTest {
            // Given
            val movieDtos = MOVIE_DTOs
            val expectedMovies = EXPECTED_MOVIES
            coEvery { exploreRemoteDataSource.getMovies() } returns movieDtos

            // When
            val result = repository.geMovies().toList()

            // Then
            assertThat(result).containsExactly(expectedMovies)
        }

    @Test
    fun `getSeries should emit list of mapped series when get valid series`() =
        runTest {
            // Given
            val seriesDtos = SERIES_DTOs
            val expectedSeries = EXPECTED_SERIES
            coEvery { exploreRemoteDataSource.getSeries() } returns seriesDtos

            // When
            val result = repository.getSeries().toList()

            // Then
            assertThat(result).containsExactly(expectedSeries)
        }

    @Test
    fun `getSeriesByGenreId should emit list of mapped series when get valid series by genre ID`() =
        runTest {
            // Given
            val genreId = 3
            val seriesDtos = SERIES_DTOs
            val expectedSeries = EXPECTED_SERIES
            coEvery { exploreRemoteDataSource.getSeriesByGenreId(genreId) } returns seriesDtos

            // When
            val result = repository.getSeriesByGenreId(genreId).toList()

            // Then
            assertThat(result).containsExactly(expectedSeries)
        }

    private companion object {
        val GENRE_DTOs = listOf(
            GenreDto(id = 1, name = "Action"),
            GenreDto(id = 2, name = "Comedy")
        )

        val EXPECTED_GENRES = listOf(
            Genre(id = 1, name = "Action"),
            Genre(id = 2, name = "Comedy")
        )

        val MOVIE_DTOs = listOf(
            MovieDto(
                id = 101,
                title = "Movie A",
                genreIds = listOf(1),
                voteAverage = 7.5f,
                releaseDate = "2023-01-01",
                adult = false,
                backdropPath = "/backdrop1.jpg",
                originalLanguage = "en",
                originalTitle = "Movie A",
                overview = "Overview A",
                posterPath = "/poster1.jpg",
                video = false,
                popularity = 100.0,
                voteCount = 1000,
                mediaType = "movie",
                firstAirDate = null
            )
        )

        val EXPECTED_MOVIES = listOf(
            Movie(
                id = 101,
                name = "Movie A",
                genreIds = listOf(1),
                rating = 7.5f,
                releaseDate = LocalDate.parse("2023-01-01"),
                adult = false,
                backdropPath = "${IMAGES_URL}/backdrop1.jpg",
                originalLanguage = "en",
                originalTitle = "Movie A",
                overview = "Overview A",
                posterPath = "${IMAGES_URL}/poster1.jpg",
                video = false,
                poster = ""
            )
        )

        val SERIES_DTOs = listOf(
            SeriesDto(
                id = 202,
                name = "Series B",
                voteAverage = 6.5f,
                adult = true,
                backdropPath = "/backdrop4.jpg",
                firstAirDate = "2023-04-01",
                genreIds = listOf(3),
                originCountry = listOf("UK"),
                originalLanguage = "en",
                originalName = "Series B",
                overview = "Overview B",
                posterPath = "/poster4.jpg",
                popularity = 400.0,
                voteCount = 2500
            )
        )

        val EXPECTED_SERIES = listOf(
            Series(
                id = 202,
                name = "Series B",
                rating = 6.5f,
                adult = true,
                backdropPath = "${IMAGES_URL}/backdrop4.jpg",
                firstAirDate = LocalDate.parse("2023-04-01"),
                genreIds = listOf(3),
                originCountry = listOf("UK"),
                originalLanguage = "en",
                originalName = "Series B",
                overview = "Overview B",
                posterPath = "${IMAGES_URL}/poster4.jpg"
            )
        )

    }

}