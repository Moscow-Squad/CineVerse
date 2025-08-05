package com.moscow.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moscow.local.dao.home.HomeCacheDao
import com.moscow.local.dao.search.ActorDao
import com.moscow.local.dao.search.FavouriteGenreDao
import com.moscow.local.dao.search.MovieDao
import com.moscow.local.dao.search.SearchHistoryDao
import com.moscow.local.dao.search.SeriesDao
import com.moscow.local.entity.ActorEntity
import com.moscow.local.entity.FavouriteGenreEntity
import com.moscow.local.entity.HomeCategoryTimestampEntity
import com.moscow.local.entity.HomeItemEntity
import com.moscow.local.entity.MovieEntity
import com.moscow.local.entity.SearchHistoryEntity
import com.moscow.local.entity.SeriesEntity

@Database(
    entities = [
        MovieEntity::class,
        SeriesEntity::class,
        ActorEntity::class,
        SearchHistoryEntity::class,
        FavouriteGenreEntity::class,
        HomeItemEntity::class,
        HomeCategoryTimestampEntity::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CineVerseDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun actorDao(): ActorDao
    abstract fun seriesDao(): SeriesDao
    abstract fun favouriteGenreDao(): FavouriteGenreDao
    abstract fun homeCacheDao(): HomeCacheDao
}