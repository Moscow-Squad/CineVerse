package com.moscow.data_source.local

import com.moscow.local.entity.HomeCategoryTimestampEntity
import com.moscow.local.entity.MediaItemEntity

interface HomeLocalDataSource {
    suspend fun insertHomeItems(homeItems: List<MediaItemEntity>)
    suspend fun updateCategoryTimestamp(timestamp: HomeCategoryTimestampEntity)
    suspend fun getHomeItemsByCategory(categoryType: String): List<MediaItemEntity>
    suspend fun getCategoryTimestamp(categoryType: String): HomeCategoryTimestampEntity?
    suspend fun clearHomeCategory(categoryType: String)
    suspend fun refreshHomeCategory(categoryType: String, homeItems: List<MediaItemEntity>)
}
