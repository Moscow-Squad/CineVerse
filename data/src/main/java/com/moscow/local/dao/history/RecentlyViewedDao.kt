package com.moscow.local.dao.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moscow.local.entity.HistoryItemEntity
@Dao
interface RecentlyViewedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentlyViewedItem(item: HistoryItemEntity)

    @Query("SELECT * FROM history_item ORDER BY cacheTimestamp DESC")
    suspend fun getRecentlyViewedItems(): List<HistoryItemEntity>

    @Query("DELETE FROM history_item WHERE id = :id")
    suspend fun deleteRecentlyViewedItemById(id: Int)
}