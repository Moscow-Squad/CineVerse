package com.moscow.local.data_source

import com.moscow.local.dao.search.FavouriteGenreDao
import com.moscow.local.entity.FavouriteGenreEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsLocalDataSourceImplTest {

    private lateinit var favouriteGenreDao: FavouriteGenreDao
    private lateinit var dataSource: DetailsLocalDataSourceImpl

    @BeforeEach
    fun setup() {
        favouriteGenreDao = mockk(relaxed = true)
        dataSource = DetailsLocalDataSourceImpl(favouriteGenreDao)
    }

    @Test
    fun `insertFavouriteGenre should increment if genre exists`() = runTest {
        val genreId = 201
        coEvery { favouriteGenreDao.getGenreById(genreId) } returns FavouriteGenreEntity(genreId, 3)

        dataSource.insertFavouriteGenre(genreId)

        coVerify { favouriteGenreDao.incrementGenreCount(genreId) }
    }

    @Test
    fun `insertFavouriteGenre should insert if not exists`() = runTest {
        val genreId = 202
        coEvery { favouriteGenreDao.getGenreById(genreId) } returns null

        dataSource.insertFavouriteGenre(genreId)

        coVerify {
            favouriteGenreDao.insertOrUpdateFavouriteGenre(
                FavouriteGenreEntity(genreId = genreId, count = 1)
            )
        }
    }

    @Test
    fun `incrementGenreCount should delegate to insertFavouriteGenre`() = runTest {
        val genreId = 203
        coEvery { favouriteGenreDao.getGenreById(genreId) } returns null

        dataSource.incrementGenreCount(genreId)

        coVerify {
            favouriteGenreDao.insertOrUpdateFavouriteGenre(
                FavouriteGenreEntity(genreId = genreId, count = 1)
            )
        }
    }

    @Test
    fun `getFavouriteGenres should return flow from DAO`() = runTest {
        val genres = listOf(FavouriteGenreEntity(1, 2), FavouriteGenreEntity(2, 5))
        coEvery { favouriteGenreDao.getFavouriteGenres() } returns flowOf(genres)

        val result = dataSource.getFavouriteGenres()

        result.collect { emitted ->
            assert(emitted == genres)
        }
    }
}