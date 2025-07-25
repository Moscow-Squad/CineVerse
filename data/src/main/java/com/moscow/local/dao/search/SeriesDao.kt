package com.moscow.local.dao.search

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moscow.local.entity.SeriesEntity

@Dao
interface SeriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(series: List<SeriesEntity>)

    @Query("SELECT * FROM series_table WHERE searchTerm = :searchTerm")
    suspend fun getSeriesBySearchTerm(searchTerm: String): List<SeriesEntity>
}