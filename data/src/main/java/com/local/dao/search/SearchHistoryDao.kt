package com.local.dao.search

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.local.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(searchHistory: SearchHistoryEntity)

    @Query("SELECT searchTerm FROM search_history_table")
     fun getAllSearchHistory(): Flow<List<String>>

    @Delete
    suspend fun deleteSearchHistory(searchHistory: SearchHistoryEntity)
}