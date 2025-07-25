package com.moscow.local.dao.search

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moscow.local.entity.FavouriteGenreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteGenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateFavouriteGenre(favouriteGenre: FavouriteGenreEntity)

    @Query("SELECT * FROM FavouriteGenre ORDER BY count DESC")
    fun getFavouriteGenres(): Flow<List<FavouriteGenreEntity>>

    @Query("SELECT * FROM FavouriteGenre WHERE genreId = :genreId")
    suspend fun getGenreById(genreId: Int): FavouriteGenreEntity?

    @Query("UPDATE FavouriteGenre SET count = count + 1 WHERE genreId = :genreId")
    suspend fun incrementGenreCount(genreId: Int)
}
