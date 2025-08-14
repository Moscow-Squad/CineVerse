package com.moscow.local.data_source

import com.google.common.truth.Truth.assertThat
import com.moscow.local.dao.history.RecentlyViewedDao
import com.moscow.local.entity.HistoryItemEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import io.mockk.*

class RecentlyViewedLocalDataSourceImplTest {

    private lateinit var recentlyViewedDao: RecentlyViewedDao
    private lateinit var recentlyViewedLocalDataSource: RecentlyViewedLocalDataSourceImpl

    @BeforeEach
    fun setUp() {
        recentlyViewedDao = mockk(relaxed = true)
        recentlyViewedLocalDataSource = RecentlyViewedLocalDataSourceImpl(recentlyViewedDao)
    }

    @Test
    fun `insertRecentlyViewedItem should delegate to dao insertRecentlyViewedItem`() = runTest {
        // Given
        val historyItem = HistoryItemEntity(
            id = 1,
            name = "Test Movie",
            posterPath = "/test-poster.jpg",
            itemType = "movie",
            rating = 8.5f,
            releaseDate = LocalDate(2023, 12, 15),
            cacheTimestamp = System.currentTimeMillis()
        )

        // When
        recentlyViewedLocalDataSource.insertRecentlyViewedItem(historyItem)

        // Then
        coVerify(exactly = 1) { recentlyViewedDao.insertRecentlyViewedItem(historyItem) }
    }

    @Test
    fun `getRecentlyViewedItems should return exact result as dao`() = runTest {
        // Given
        val expectedList = listOf(
            HistoryItemEntity(
                id = 1,
                name = "Movie 1",
                posterPath = "/poster1.jpg",
                itemType = "movie",
                rating = 7.8f,
                releaseDate = LocalDate(2023, 6, 10),
                cacheTimestamp = 1698765432000L
            ),
            HistoryItemEntity(
                id = 2,
                name = "TV Show 1",
                posterPath = "/poster2.jpg",
                itemType = "tv",
                rating = 8.2f,
                releaseDate = LocalDate(2022, 3, 20),
                cacheTimestamp = 1698765431000L
            )
        )
        every { recentlyViewedDao.getRecentlyViewedItems() } returns flowOf(expectedList)

        // When
        val result = recentlyViewedLocalDataSource.getRecentlyViewedItems().first()

        // Then
        assertThat(result).isEqualTo(expectedList)
        verify(exactly = 1) { recentlyViewedDao.getRecentlyViewedItems() }
    }

    @Test
    fun `getRecentlyViewedItems should return empty list when dao returns empty`() = runTest {
        // Given
        val emptyList = emptyList<HistoryItemEntity>()
        every { recentlyViewedDao.getRecentlyViewedItems() } returns flowOf(emptyList)

        // When
        val result = recentlyViewedLocalDataSource.getRecentlyViewedItems().first()

        // Then
        assertThat(result).isEmpty()
        verify(exactly = 1) { recentlyViewedDao.getRecentlyViewedItems() }
    }

    @Test
    fun `deleteRecentlyViewedItemById should delegate to dao deleteRecentlyViewedItemById`() = runTest {
        // Given
        val itemId = 5

        // When
        recentlyViewedLocalDataSource.deleteRecentlyViewedItemById(itemId)

        // Then
        coVerify(exactly = 1) { recentlyViewedDao.deleteRecentlyViewedItemById(itemId) }
    }

    @Test
    fun `insertRecentlyViewedItem should handle item with null releaseDate`() = runTest {
        // Given
        val historyItemWithNullDate = HistoryItemEntity(
            id = 3,
            name = "Unknown Date Movie",
            posterPath = "/unknown-poster.jpg",
            itemType = "movie",
            rating = 6.5f,
            releaseDate = null,
            cacheTimestamp = System.currentTimeMillis()
        )

        // When
        recentlyViewedLocalDataSource.insertRecentlyViewedItem(historyItemWithNullDate)

        // Then
        coVerify(exactly = 1) { recentlyViewedDao.insertRecentlyViewedItem(historyItemWithNullDate) }
    }

    @Test
    fun `deleteRecentlyViewedItemById should handle different id types`() = runTest {

        val largeId = 999999

        recentlyViewedLocalDataSource.deleteRecentlyViewedItemById(largeId)

        coVerify(exactly = 1) { recentlyViewedDao.deleteRecentlyViewedItemById(largeId) }
    }
}