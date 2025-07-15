package com.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.local.dao.search.ActorDao
import com.local.dao.search.MovieDao
import com.local.dao.search.SearchHistoryDao
import com.local.dao.search.SeriesDao
import com.local.entity.ActorEntity
import com.local.entity.MovieEntity
import com.local.entity.SearchHistoryEntity
import com.local.entity.SeriesEntity

@Database(
    entities = [
        MovieEntity::class,
        SeriesEntity::class,
        ActorEntity::class,
        SearchHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CineVerseDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun actorDao(): ActorDao
    abstract fun seriesDao(): SeriesDao
}