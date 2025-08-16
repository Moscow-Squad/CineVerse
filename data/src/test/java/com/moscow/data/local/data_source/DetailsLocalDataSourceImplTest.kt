package com.moscow.data.local.data_source

import com.google.common.truth.Truth.assertThat
import com.moscow.local.dao.search.FavouriteGenreDao
import com.moscow.local.data_source.DetailsLocalDataSourceImpl
import com.moscow.local.entity.FavouriteGenreEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import io.mockk.*

class DetailsLocalDataSourceImplTest {

    private lateinit var favouriteGenreDao: FavouriteGenreDao
    private lateinit var detailsLocalDataSource: DetailsLocalDataSourceImpl

    @BeforeEach
    fun setUp() {
        favouriteGenreDao = mockk(relaxed = true)
        detailsLocalDataSource = DetailsLocalDataSourceImpl(favouriteGenreDao)
    }

    @Test
    fun `insertFavouriteGenre should inserts new genre if not exists`() = runTest {

        val genreId = 1
        coEvery { favouriteGenreDao.getGenreById(genreId) } returns null

        detailsLocalDataSource.insertFavouriteGenre(genreId)

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

        detailsLocalDataSource.insertFavouriteGenre(genreId)

        coVerify(exactly = 1) { favouriteGenreDao.incrementGenreCount(genreId) }
        coVerify(exactly = 0) { favouriteGenreDao.insertOrUpdateFavouriteGenre(any()) }
    }

    @Test
    fun `getFavouriteGenres should returns exact result as dao`() = runTest {

        val expectedList = listOf(
            FavouriteGenreEntity(genreId = 1, count = 2),
            FavouriteGenreEntity(genreId = 2, count = 5)
        )
        every { favouriteGenreDao.getFavouriteGenres() } returns flowOf(expectedList)

        val result = detailsLocalDataSource.getFavouriteGenres().first()

        assertThat(result).isEqualTo(expectedList)
        verify(exactly = 1) { favouriteGenreDao.getFavouriteGenres() }
    }
}