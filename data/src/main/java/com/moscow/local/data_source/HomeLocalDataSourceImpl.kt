package com.moscow.local.data_source

import com.moscow.data_source.local.HomeLocalDataSource
import com.moscow.local.dao.home.HomeCacheDao
import com.moscow.local.entity.HomeCategoryTimestampEntity
import com.moscow.local.entity.MediaItemEntity
import javax.inject.Inject

class HomeLocalDataSourceImpl @Inject constructor(
    private val homeCacheDao: HomeCacheDao
) : HomeLocalDataSource {

    override suspend fun insertHomeItems(
        homeItems: List<MediaItemEntity>
    ) = homeCacheDao.insertHomeItems(homeItems = homeItems)

    override suspend fun updateCategoryTimestamp(
        timestamp: HomeCategoryTimestampEntity
    ) = homeCacheDao.updateCategoryTimestamp(timestamp = timestamp)

    override suspend fun getHomeItemsByCategory(
        categoryType: String
    ): List<MediaItemEntity> = homeCacheDao.getHomeItemsByCategory(categoryType = categoryType)

    override suspend fun getCategoryTimestamp(
        categoryType: String
    ): HomeCategoryTimestampEntity? = homeCacheDao.getCategoryTimestamp(categoryType = categoryType)

    override suspend fun clearHomeCategory(
        categoryType: String
    ) = homeCacheDao.clearHomeCategory(categoryType = categoryType)

    override suspend fun refreshHomeCategory(
        categoryType: String,
        homeItems: List<MediaItemEntity>
    ) = homeCacheDao.refreshHomeCategory(
        categoryType = categoryType,
        homeItems = homeItems
    )

    override suspend fun clearHomeCash() {
        homeCacheDao.clearHomeItemCash()
        homeCacheDao.clearHomeCategoryTimestampCash()
    }
}