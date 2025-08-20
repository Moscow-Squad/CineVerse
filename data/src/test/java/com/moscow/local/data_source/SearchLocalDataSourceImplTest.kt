package com.moscow.local.data_source

import androidx.work.WorkManager
import com.moscow.local.dao.search.FavouriteGenreDao
import com.moscow.local.dao.search.SearchHistoryDao
import com.moscow.local.entity.FavouriteGenreEntity
import com.moscow.local.entity.SearchHistoryEntity
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import androidx.work.OneTimeWorkRequest

@OptIn(ExperimentalCoroutinesApi::class)
class SearchLocalDataSourceImplTest {

    private lateinit var searchHistoryDao: SearchHistoryDao
    private lateinit var favouriteGenreDao: FavouriteGenreDao
    private lateinit var workManager: WorkManager
    private lateinit var dataSource: SearchLocalDataSourceImpl

    @BeforeEach
    fun setup() {
        searchHistoryDao = mockk(relaxed = true)
        favouriteGenreDao = mockk(relaxed = true)
        workManager = mockk(relaxed = true)
        dataSource = SearchLocalDataSourceImpl(searchHistoryDao, favouriteGenreDao, workManager)
    }

    @Test
    fun `getAllSearchHistory should return flow from DAO`() = runTest {
        val history = listOf("Inception", "Interstellar")
        every { searchHistoryDao.getAllSearchHistory() } returns flowOf(history)

        val result = dataSource.getAllSearchHistory()

        result.collect {
            assert(it == history)
        }
    }

    @Test
    fun `insertSearchHistory should insert and enqueue delete worker`() = runTest {
        val query = "Inception"

        dataSource.insertSearchHistory(query)

        coVerify { searchHistoryDao.insertSearchHistory(SearchHistoryEntity(searchTerm = query)) }
        verify { workManager.enqueue(any<OneTimeWorkRequest>()) }
    }

    @Test
    fun `deleteSearchHistory should call DAO with correct entity`() = runTest {
        val query = "Interstellar"

        dataSource.deleteSearchHistory(query)

        coVerify { searchHistoryDao.deleteSearchHistory(SearchHistoryEntity(searchTerm = query)) }
    }

    @Test
    fun `deleteAllSearchHistory should call DAO`() = runTest {
        dataSource.deleteAllSearchHistory()

        coVerify { searchHistoryDao.deleteAllSearchHistory() }
    }

    @Test
    fun `getFavouriteGenres should return flow from DAO`() = runTest {
        val genres = listOf(FavouriteGenreEntity(1, 3), FavouriteGenreEntity(2, 5))
        every { favouriteGenreDao.getFavouriteGenres() } returns flowOf(genres)

        val result = dataSource.getFavouriteGenres()

        result.collect {
            assert(it == genres)
        }
    }

    @Test
    fun `insertFavouriteGenre should increment count if genre exists`() = runTest {
        val genreId = 101
        coEvery { favouriteGenreDao.getGenreById(genreId) } returns FavouriteGenreEntity(genreId, 2)

        dataSource.insertFavouriteGenre(genreId)

        coVerify { favouriteGenreDao.incrementGenreCount(genreId) }
    }

    @Test
    fun `insertFavouriteGenre should insert new genre if not exists`() = runTest {
        val genreId = 102
        coEvery { favouriteGenreDao.getGenreById(genreId) } returns null

        dataSource.insertFavouriteGenre(genreId)

        coVerify {
            favouriteGenreDao.insertOrUpdateFavouriteGenre(
                FavouriteGenreEntity(genreId = genreId, count = 1)
            )
        }
    }
}