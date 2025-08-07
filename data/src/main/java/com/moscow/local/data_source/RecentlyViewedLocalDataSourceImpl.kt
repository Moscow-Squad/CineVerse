package com.moscow.local.data_source

import com.moscow.data_source.local.RecentlyViewedLocalDataSource
import com.moscow.local.dao.history.RecentlyViewedDao
import com.moscow.local.entity.HistoryItemEntity
import javax.inject.Inject

class RecentlyViewedLocalDataSourceImpl @Inject constructor(
    private val recentlyViewedDao: RecentlyViewedDao
) : RecentlyViewedLocalDataSource {

    override suspend fun insertRecentlyViewedItem(item: HistoryItemEntity) {
        recentlyViewedDao.insertRecentlyViewedItem(item)
    }

    override suspend fun getRecentlyViewedItems(): List<HistoryItemEntity> {
        return recentlyViewedDao.getRecentlyViewedItems()
    }

    override suspend fun deleteRecentlyViewedItemById(id: Int) {
        return recentlyViewedDao.deleteRecentlyViewedItemById(id)
    }
}