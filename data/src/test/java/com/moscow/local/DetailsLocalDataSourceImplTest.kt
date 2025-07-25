package com.moscow.local

import com.google.common.truth.Truth
import com.moscow.local.dao.search.FavouriteGenreDao
import com.moscow.local.entity.FavouriteGenreEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class DetailsLocalDataSourceImplTest {

    private val favouriteGenreDao: FavouriteGenreDao = mockk(relaxed = true)
    private lateinit var detailsLocalDataSource: DetailsLocalDataSourceImpl

    @BeforeEach
    fun setup() {
        detailsLocalDataSource = DetailsLocalDataSourceImpl(favouriteGenreDao)
    }

    @Test
    fun `insertFavouriteGenre should inserts new genre if not exists`() = runTest {
        // Given
        val genreId = 1
        coEvery { favouriteGenreDao.getGenreById(genreId) } returns null

        // When
        detailsLocalDataSource.insertFavouriteGenre(genreId)

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
        detailsLocalDataSource.insertFavouriteGenre(genreId)

        // Then
        coVerify(exactly = 1) { favouriteGenreDao.incrementGenreCount(genreId) }
        coVerify(exactly = 0) {
            favouriteGenreDao.insertOrUpdateFavouriteGenre(any())
        }
    }

    @Test
    fun `getFavouriteGenres should returns exact result as dao`() = runTest {
        // Given
        val expectedList = listOf(
            FavouriteGenreEntity(genreId = 1, count = 2),
            FavouriteGenreEntity(genreId = 2, count = 5)
        )
        every { favouriteGenreDao.getFavouriteGenres() } returns flowOf(expectedList)

        // When
        val result = detailsLocalDataSource.getFavouriteGenres().first()

        // Then
        Truth.assertThat(result).isEqualTo(expectedList)
    }
}
