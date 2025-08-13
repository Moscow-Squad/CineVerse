package com.moscow.data_source.local

import com.moscow.local.entity.HistoryItemEntity
import kotlinx.coroutines.flow.Flow

interface RecentlyViewedLocalDataSource {
    suspend fun insertRecentlyViewedItem(item: HistoryItemEntity)
    fun getRecentlyViewedItems(): Flow<List<HistoryItemEntity>>
    suspend fun deleteRecentlyViewedItemById(id: Int)
}