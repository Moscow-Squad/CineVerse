package com.moscow.local

import com.google.common.truth.Truth
import com.moscow.local.dao.search.ActorDao
import com.moscow.local.dao.search.FavouriteGenreDao
import com.moscow.local.dao.search.MovieDao
import com.moscow.local.dao.search.SearchHistoryDao
import com.moscow.local.dao.search.SeriesDao
import com.moscow.local.entity.ActorEntity
import com.moscow.local.entity.FavouriteGenreEntity
import com.moscow.local.entity.Gender
import com.moscow.local.entity.MovieEntity
import com.moscow.local.entity.SearchHistoryEntity
import com.moscow.local.entity.SeriesEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class SearchLocalDataSourceImplTest {

    private val searchHistoryDao: SearchHistoryDao = mockk(relaxed = true)
    private val movieDao: MovieDao = mockk(relaxed = true)
    private val actorDao: ActorDao = mockk(relaxed = true)
    private val seriesDao: SeriesDao = mockk(relaxed = true)
    private val favouriteGenreDao: FavouriteGenreDao = mockk(relaxed = true)

    private lateinit var searchLocalDataSource: SearchLocalDataSourceImpl

    @BeforeEach
    fun setup() {
        searchLocalDataSource = SearchLocalDataSourceImpl(
            searchHistoryDao = searchHistoryDao,
            movieDao = movieDao,
            actorDao = actorDao,
            seriesDao = seriesDao,
            favouriteGenreDao = favouriteGenreDao
        )
    }

    @Test
    fun `getAllSearchHistory should returns exact result as dao`() = runTest {
        // Given
        val expectedList = listOf("nour", "hoda")
        coEvery { searchHistoryDao.getAllSearchHistory() } returns flowOf(expectedList)

        // When
        val result = searchLocalDataSource.getAllSearchHistory().first()

        // Then
        Truth.assertThat(result).isEqualTo(expectedList)
    }

    @Test
    fun `insertSearchHistory should call dao' insertSearchHistory`() = runTest {
        // Given
        val searchTerm = "nour"
        val historyTerm = SearchHistoryEntity(searchTerm)

        // When
        searchLocalDataSource.insertSearchHistory(searchTerm)

        // Then
        coVerify(exactly = 1) { searchHistoryDao.insertSearchHistory(historyTerm) }
    }

    @Test
    fun `deleteSearchHistory should call dao' deleteSearchHistory`() = runTest {
        // Given
        val searchTerm = "nour"
        val historyTerm = SearchHistoryEntity(searchTerm)

        // When
        searchLocalDataSource.deleteSearchHistory(searchTerm)

        // Then
        coVerify(exactly = 1) { searchHistoryDao.deleteSearchHistory(historyTerm) }
    }

    @Test
    fun `deleteAllSearchHistory should call dao' deleteAllSearchHistory`() = runTest {
        // Given & When
        searchLocalDataSource.deleteAllSearchHistory()

        // Then
        coVerify(exactly = 1) { searchHistoryDao.deleteAllSearchHistory() }
    }

    @Test
    fun `insertMovie should call dao' insertMovies`() = runTest {
        // Given
        val searchTerm = "nour"
        val movieEntities = listOf(movieEntity1, movieEntity2)
        val data = movieEntities.map { movie -> movie.copy(searchTerm = searchTerm) }

        // When
        searchLocalDataSource.insertMovie(movieEntities, searchTerm)

        // Then
        coVerify(exactly = 1) { movieDao.insertMovies(data) }
    }

    @Test
    fun `getMoviesBySearchTerm should returns exact result as dao`() = runTest {
        // Given
        val searchQuery = "nour"
        val expectedList = listOf( movieEntity1, movieEntity2)
        coEvery { movieDao.getMoviesBySearchTerm(searchQuery) } returns expectedList

        // When
        val result = searchLocalDataSource.getMoviesBySearchTerm(searchQuery)

        // Then
        Truth.assertThat(result).isEqualTo(expectedList)
    }

    @Test
    fun `insertActors should call dao' insertActors`() = runTest {
        // Given
        val searchTerm = "nour"
        val actorEntities = listOf(actorEntity1, actorEntity2)
        val data = actorEntities.map { actor -> actor.copy(searchTerm = searchTerm) }

        // When
        searchLocalDataSource.insertActors(actorEntities, searchTerm)

        // Then
        coVerify(exactly = 1) { actorDao.insertActors(data) }
    }

    @Test
    fun `getActorsBySearchTerm should returns exact result as dao`() = runTest {
        // Given
        val searchQuery = "nour"
        val expectedList = listOf( actorEntity1, actorEntity2)
        coEvery { actorDao.getActorsBySearchTerm(searchQuery) } returns expectedList

        // When
        val result = searchLocalDataSource.getActorsBySearchTerm(searchQuery)

        // Then
        Truth.assertThat(result).isEqualTo(expectedList)
    }

    @Test
    fun `insertSeries should call dao' insertSeries`() = runTest {
        // Given
        val searchTerm = "nour"
        val seriesEntities = listOf(seriesEntity1, seriesEntity2)
        val data = seriesEntities.map { series -> series.copy(searchTerm = searchTerm) }

        // When
        searchLocalDataSource.insertSeries(seriesEntities, searchTerm)

        // Then
        coVerify(exactly = 1) { seriesDao.insertSeries(data) }
    }

    @Test
    fun `getSeriesBySearchTerm should returns exact result as dao`() = runTest {
        // Given
        val searchQuery = "nour"
        val expectedList = listOf( seriesEntity1, seriesEntity2)
        coEvery { seriesDao.getSeriesBySearchTerm(searchQuery) } returns expectedList

        // When
        val result = searchLocalDataSource.getSeriesBySearchTerm(searchQuery)

        // Then
        Truth.assertThat(result).isEqualTo(expectedList)
    }

    @Test
    fun `getFavouriteGenres should returns exact result as dao`() = runTest{
        // Given
        val expectedList = listOf(
            FavouriteGenreEntity(genreId = 1, count = 2),
            FavouriteGenreEntity(genreId = 2, count = 5)
        )
        every { favouriteGenreDao.getFavouriteGenres() } returns flowOf(expectedList)

        // When
        val result = searchLocalDataSource.getFavouriteGenres().first()

        // Then
        Truth.assertThat(result).isEqualTo(expectedList)
    }

    @Test
    fun `insertFavouriteGenre should inserts new genre if not exists`() = runTest {
        // Given
        val genreId = 1
        coEvery { favouriteGenreDao.getGenreById(genreId) } returns null

        // When
        searchLocalDataSource.insertFavouriteGenre(genreId)

        // Then
        coVerify(exactly = 1) {
            favouriteGenreDao.insertOrUpdateFavouriteGenre(
                FavouriteGenreEntity(genreId = genreId, count = 1)
            )
        }
        coVerify(exactly = 0) { favouriteGenreDao.incrementGenreCount(any()) }
    }

    @Test
    fun `insertFavouriteGenre should increments genre count if exists`() = runTest {
        // Given
        val genreId = 2
        val existingEntity = FavouriteGenreEntity(genreId = genreId, count = 5)
        coEvery { favouriteGenreDao.getGenreById(genreId) } returns existingEntity

        // When
        searchLocalDataSource.insertFavouriteGenre(genreId)

        // Then
        coVerify(exactly = 1) { favouriteGenreDao.incrementGenreCount(genreId) }
        coVerify(exactly = 0) {
            favouriteGenreDao.insertOrUpdateFavouriteGenre(any())
        }
    }

    companion object{
        val movieEntity1 = MovieEntity(
            id = 0,
            searchTerm = "nour",
            name = "movie1",
            rating = 2f,
            genresId = listOf(1,2),
            releaseDate = "",
            poster = "",
            duration = ""
        )
        val movieEntity2 = MovieEntity(
            id = 1,
            searchTerm = "nour",
            name = "movie1",
            rating = 2f,
            genresId = listOf(1,2),
            releaseDate = "",
            poster = "",
            duration = ""
        )
        val actorEntity1 = ActorEntity(
            id = 0,
            searchTerm = "nour",
            name = "actor1",
            gender = Gender.FEMALE,
            profileImg = ""
        )
        val actorEntity2 = ActorEntity(
            id = 1,
            searchTerm = "nour",
            name = "actor1",
            gender = Gender.FEMALE,
            profileImg = ""
        )
        val seriesEntity1 = SeriesEntity(
            id = 1,
            searchTerm = "nour",
            name = "actor1",
            genresId = listOf(1,2),
            description = "",
            rating = 2f,
            releaseDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            poster = "",
        )
        val seriesEntity2 = SeriesEntity(
            id = 1,
            searchTerm = "nour",
            name = "actor1",
            genresId = listOf(1,2),
            description = "",
            rating = 2f,
            releaseDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            poster = "",
        )
    }
}
