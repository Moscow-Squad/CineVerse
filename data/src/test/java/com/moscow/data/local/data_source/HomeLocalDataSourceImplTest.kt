package com.moscow.data.local.data_source

import com.google.common.truth.Truth.assertThat
import com.moscow.local.dao.home.HomeCacheDao
import com.moscow.local.data_source.HomeLocalDataSourceImpl
import com.moscow.local.entity.HomeCategoryTimestampEntity
import com.moscow.local.entity.MediaItemEntity
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import io.mockk.*

class HomeLocalDataSourceImplTest {

    private lateinit var homeCacheDao: HomeCacheDao
    private lateinit var homeLocalDataSource: HomeLocalDataSourceImpl

    @BeforeEach
    fun setUp() {
        homeCacheDao = mockk(relaxed = true)
        homeLocalDataSource = HomeLocalDataSourceImpl(homeCacheDao)
    }

    @Test
    fun `insertHomeItems should delegate to dao insertHomeItems`() = runTest {

        val homeItems = listOf(
            MediaItemEntity(
                id = 1,
                itemId = 101,
                categoryType = "popular",
                name = "Test Movie 1",
                posterPath = "/poster1.jpg",
                backdropPath = "/backdrop1.jpg",
                itemType = "movie",
                rating = 8.5f,
                genreIds = listOf(28, 12),
                releaseDate = LocalDate(2023, 6, 15),
                cacheTimestamp = System.currentTimeMillis()
            ),
            MediaItemEntity(
                id = 2,
                itemId = 102,
                categoryType = "popular",
                name = "Test Movie 2",
                posterPath = "/poster2.jpg",
                backdropPath = "/backdrop2.jpg",
                itemType = "movie",
                rating = 7.8f,
                genreIds = listOf(35, 18),
                releaseDate = LocalDate(2023, 8, 20),
                cacheTimestamp = System.currentTimeMillis()
            )
        )

        homeLocalDataSource.insertHomeItems(homeItems)

        coVerify(exactly = 1) { homeCacheDao.insertHomeItems(homeItems) }
    }

    @Test
    fun `updateCategoryTimestamp should delegate to dao updateCategoryTimestamp`() = runTest {

        val timestamp = HomeCategoryTimestampEntity(
            categoryType = "trending",
            lastRefreshed = System.currentTimeMillis()
        )

        homeLocalDataSource.updateCategoryTimestamp(timestamp)

        coVerify(exactly = 1) { homeCacheDao.updateCategoryTimestamp(timestamp) }
    }

    @Test
    fun `getHomeItemsByCategory should return exact result as dao`() = runTest {

        val categoryType = "top_rated"
        val expectedItems = listOf(
            MediaItemEntity(
                id = 3,
                itemId = 103,
                categoryType = categoryType,
                name = "Top Movie",
                posterPath = "/top-poster.jpg",
                backdropPath = "/top-backdrop.jpg",
                itemType = "movie",
                rating = 9.2f,
                genreIds = listOf(18, 80),
                releaseDate = LocalDate(2023, 4, 10),
                cacheTimestamp = 1698765432000L
            )
        )
        coEvery { homeCacheDao.getHomeItemsByCategory(categoryType) } returns expectedItems

        val result = homeLocalDataSource.getHomeItemsByCategory(categoryType)

        assertThat(result).isEqualTo(expectedItems)
    }

    @Test
    fun `getCategoryTimestamp should return exact result as dao`() = runTest {
        // Given
        val categoryType = "upcoming"
        val expectedTimestamp = HomeCategoryTimestampEntity(
            categoryType = categoryType,
            lastRefreshed = 1698765432000L
        )
        coEvery { homeCacheDao.getCategoryTimestamp(categoryType) } returns expectedTimestamp

        val result = homeLocalDataSource.getCategoryTimestamp(categoryType)

        assertThat(result).isEqualTo(expectedTimestamp)
    }

    @Test
    fun `getCategoryTimestamp should return null when dao returns null`() = runTest {

        val categoryType = "non_existent"
        coEvery { homeCacheDao.getCategoryTimestamp(categoryType) } returns null

        val result = homeLocalDataSource.getCategoryTimestamp(categoryType)

        assertThat(result).isNull()
    }

    @Test
    fun `clearHomeCategory should delegate to dao clearHomeCategory`() = runTest {

        val categoryType = "now_playing"

        homeLocalDataSource.clearHomeCategory(categoryType)

        coVerify(exactly = 1) { homeCacheDao.clearHomeCategory(categoryType) }
    }

    @Test
    fun `refreshHomeCategory should delegate to dao refreshHomeCategory`() = runTest {

        val categoryType = "popular"
        val homeItems = listOf(
            MediaItemEntity(
                id = 4,
                itemId = 104,
                categoryType = categoryType,
                name = "Refresh Movie",
                posterPath = "/refresh-poster.jpg",
                backdropPath = "/refresh-backdrop.jpg",
                itemType = "movie",
                rating = 8.0f,
                genreIds = listOf(28, 53),
                releaseDate = LocalDate(2023, 9, 5),
                cacheTimestamp = System.currentTimeMillis()
            )
        )

        homeLocalDataSource.refreshHomeCategory(categoryType, homeItems)

        coVerify(exactly = 1) { homeCacheDao.refreshHomeCategory(categoryType, homeItems) }
    }

    @Test
    fun `clearHomeCash should call both clear methods in dao`() = runTest {

        homeLocalDataSource.clearHomeCash()

        coVerify(exactly = 1) { homeCacheDao.clearHomeItemCash() }
        coVerify(exactly = 1) { homeCacheDao.clearHomeCategoryTimestampCash() }
    }

    @Test
    fun `getHomeItemsByCategory should handle empty list from dao`() = runTest {

        val categoryType = "empty_category"
        val emptyList = emptyList<MediaItemEntity>()
        coEvery { homeCacheDao.getHomeItemsByCategory(categoryType) } returns emptyList

        val result = homeLocalDataSource.getHomeItemsByCategory(categoryType)

        assertThat(result).isEmpty()
    }

    @Test
    fun `insertHomeItems should handle empty list`() = runTest {

        val emptyList = emptyList<MediaItemEntity>()

        homeLocalDataSource.insertHomeItems(emptyList)

        coVerify(exactly = 1) { homeCacheDao.insertHomeItems(emptyList) }
    }

    @Test
    fun `insertHomeItems should handle items with null releaseDate`() = runTest {

        val itemsWithNullDate = listOf(
            MediaItemEntity(
                id = 5,
                itemId = 105,
                categoryType = "unknown_date",
                name = "No Date Movie",
                posterPath = "/no-date-poster.jpg",
                backdropPath = "/no-date-backdrop.jpg",
                itemType = "movie",
                rating = 6.5f,
                genreIds = listOf(99),
                releaseDate = null,
                cacheTimestamp = System.currentTimeMillis()
            )
        )

        homeLocalDataSource.insertHomeItems(itemsWithNullDate)

        coVerify(exactly = 1) { homeCacheDao.insertHomeItems(itemsWithNullDate) }
    }

    @Test
    fun `insertHomeItems should handle items with empty genreIds`() = runTest {

        val itemsWithEmptyGenres = listOf(
            MediaItemEntity(
                id = 6,
                itemId = 106,
                categoryType = "no_genre",
                name = "No Genre Movie",
                posterPath = "/no-genre-poster.jpg",
                backdropPath = "/no-genre-backdrop.jpg",
                itemType = "tv",
                rating = 7.2f,
                genreIds = emptyList(),
                releaseDate = LocalDate(2023, 11, 1),
                cacheTimestamp = System.currentTimeMillis()
            )
        )

        homeLocalDataSource.insertHomeItems(itemsWithEmptyGenres)

        coVerify(exactly = 1) { homeCacheDao.insertHomeItems(itemsWithEmptyGenres) }
    }
}