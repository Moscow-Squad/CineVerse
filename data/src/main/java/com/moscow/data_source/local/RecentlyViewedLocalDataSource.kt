package com.moscow.data_source.local

import com.moscow.local.entity.HistoryItemEntity

interface RecentlyViewedLocalDataSource {
    suspend fun insertRecentlyViewedItem(item: HistoryItemEntity)
    suspend fun getRecentlyViewedItems(): List<HistoryItemEntity>
}