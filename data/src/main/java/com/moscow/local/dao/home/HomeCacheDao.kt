package com.moscow.local.dao.home

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.moscow.local.entity.HomeCategoryTimestampEntity
import com.moscow.local.entity.MediaItemEntity

@Dao
interface HomeCacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHomeItems(homeItems: List<MediaItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCategoryTimestamp(timestamp: HomeCategoryTimestampEntity)

    @Query("SELECT * FROM home_item WHERE categoryType = :categoryType")
    suspend fun getHomeItemsByCategory(categoryType: String): List<MediaItemEntity>

    @Query("SELECT * FROM home_category_timestamp WHERE categoryType = :categoryType")
    suspend fun getCategoryTimestamp(categoryType: String): HomeCategoryTimestampEntity?

    @Query("DELETE FROM home_item WHERE categoryType = :categoryType")
    suspend fun clearHomeCategory(categoryType: String)

    @Transaction
    suspend fun refreshHomeCategory(categoryType: String, homeItems: List<MediaItemEntity>) {
        clearHomeCategory(categoryType)
        insertHomeItems(homeItems)
        updateCategoryTimestamp(HomeCategoryTimestampEntity(categoryType))
    }
}