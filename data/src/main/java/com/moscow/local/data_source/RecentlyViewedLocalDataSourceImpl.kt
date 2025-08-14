package com.moscow.local.data_source

import com.moscow.data_source.local.RecentlyViewedLocalDataSource
import com.moscow.local.dao.history.RecentlyViewedDao
import com.moscow.local.entity.HistoryItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecentlyViewedLocalDataSourceImpl @Inject constructor(
    private val recentlyViewedDao: RecentlyViewedDao
) : RecentlyViewedLocalDataSource {

    override suspend fun insertRecentlyViewedItem(
        item: HistoryItemEntity
    ) = recentlyViewedDao.insertRecentlyViewedItem(item = item)

    override fun getRecentlyViewedItems(): Flow<List<HistoryItemEntity>> =
        recentlyViewedDao.getRecentlyViewedItems()

    override suspend fun deleteRecentlyViewedItemById(
        id: Int
    ) = recentlyViewedDao.deleteRecentlyViewedItemById(id = id)
}