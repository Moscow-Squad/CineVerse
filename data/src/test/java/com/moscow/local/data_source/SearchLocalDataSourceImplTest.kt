package com.moscow.local.data_source

import com.google.common.truth.Truth
import com.moscow.local.dao.search.FavouriteGenreDao
import com.moscow.local.dao.search.SearchHistoryDao
import com.moscow.local.entity.FavouriteGenreEntity
import com.moscow.local.entity.SearchHistoryEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchLocalDataSourceImplTest {

    private val searchHistoryDao: SearchHistoryDao = mockk(relaxed = true)
    private val favouriteGenreDao: FavouriteGenreDao = mockk(relaxed = true)

    private lateinit var searchLocalDataSource: SearchLocalDataSourceImpl

    @BeforeEach
    fun setup() {
        searchLocalDataSource = SearchLocalDataSourceImpl(
            searchHistoryDao = searchHistoryDao,
            favouriteGenreDao = favouriteGenreDao
        )
    }

    @Test
    fun `getAllSearchHistory should returns exact result as dao`() = runTest {

        val expectedList = listOf("nour", "hoda")
        coEvery { searchHistoryDao.getAllSearchHistory() } returns flowOf(expectedList)

        val result = searchLocalDataSource.getAllSearchHistory().first()

        Truth.assertThat(result).isEqualTo(expectedList)
    }

    @Test
    fun `insertSearchHistory should call dao insertSearchHistory`() = runTest {

        val searchTerm = "nour"
        val historyTerm = SearchHistoryEntity(searchTerm)

        searchLocalDataSource.insertSearchHistory(searchTerm)

        coVerify(exactly = 1) { searchHistoryDao.insertSearchHistory(historyTerm) }
    }

    @Test
    fun `deleteSearchHistory should call dao deleteSearchHistory`() = runTest {

        val searchTerm = "farah"
        val historyTerm = SearchHistoryEntity(searchTerm)

        searchLocalDataSource.deleteSearchHistory(searchTerm)

        coVerify(exactly = 1) { searchHistoryDao.deleteSearchHistory(historyTerm) }
    }

    @Test
    fun `deleteAllSearchHistory should call dao deleteAllSearchHistory`() = runTest {

        searchLocalDataSource.deleteAllSearchHistory()

        coVerify(exactly = 1) { searchHistoryDao.deleteAllSearchHistory() }
    }

    @Test
    fun `getFavouriteGenres should returns exact result as dao`() = runTest {

        val expectedList = listOf(
            FavouriteGenreEntity(genreId = 1, count = 2),
            FavouriteGenreEntity(genreId = 2, count = 5)
        )
        every { favouriteGenreDao.getFavouriteGenres() } returns flowOf(expectedList)

        val result = searchLocalDataSource.getFavouriteGenres().first()

        Truth.assertThat(result).isEqualTo(expectedList)
    }

    @Test
    fun `insertFavouriteGenre should inserts new genre if not exists`() = runTest {

        val genreId = 1
        coEvery { favouriteGenreDao.getGenreById(genreId) } returns null

        searchLocalDataSource.insertFavouriteGenre(genreId)

        coVerify(exactly = 1) {
            favouriteGenreDao.insertOrUpdateFavouriteGenre(
                FavouriteGenreEntity(genreId = genreId, count = 1)
            )
        }
        coVerify(exactly = 0) { favouriteGenreDao.incrementGenreCount(any()) }
    }

    @Test
    fun `insertFavouriteGenre should increments genre count if exists`() = runTest {

        val genreId = 2
        val existingEntity = FavouriteGenreEntity(genreId = genreId, count = 5)
        coEvery { favouriteGenreDao.getGenreById(genreId) } returns existingEntity

        searchLocalDataSource.insertFavouriteGenre(genreId)

        coVerify(exactly = 1) { favouriteGenreDao.incrementGenreCount(genreId) }
        coVerify(exactly = 0) {
            favouriteGenreDao.insertOrUpdateFavouriteGenre(any())
        }
    }
}