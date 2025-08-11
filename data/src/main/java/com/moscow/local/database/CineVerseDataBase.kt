package com.moscow.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moscow.local.dao.history.RecentlyViewedDao
import com.moscow.local.dao.home.HomeCacheDao
import com.moscow.local.dao.search.FavouriteGenreDao
import com.moscow.local.dao.search.SearchHistoryDao
import com.moscow.local.entity.FavouriteGenreEntity
import com.moscow.local.entity.HistoryItemEntity
import com.moscow.local.entity.HomeCategoryTimestampEntity
import com.moscow.local.entity.MediaItemEntity
import com.moscow.local.entity.SearchHistoryEntity

@Database(
    entities = [
        SearchHistoryEntity::class,
        FavouriteGenreEntity::class,
        MediaItemEntity::class,
        HomeCategoryTimestampEntity::class,
        HistoryItemEntity::class
    ],
    version = 7,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CineVerseDataBase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun favouriteGenreDao(): FavouriteGenreDao
    abstract fun homeCacheDao(): HomeCacheDao
    abstract fun recentlyViewedDao(): RecentlyViewedDao
}