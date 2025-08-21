package com.moscow.local.dao.genre

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moscow.local.entity.GenreEntity

@Dao
interface GenreDao {
    @Query("SELECT * FROM genres")
    suspend fun getAllGenres(): List<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreEntity>)

    @Query("DELETE FROM genres")
    suspend fun clearGenres()
}