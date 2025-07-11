package com.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.local.dao.search.MovieDao
import com.local.dao.search.SearchHistoryDao
import com.local.entity.MovieEntity
import com.local.entity.SearchHistoryEntity

@Database(
    entities = [MovieEntity::class, SearchHistoryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CineVerseDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun searchHistoryDao(): SearchHistoryDao
}