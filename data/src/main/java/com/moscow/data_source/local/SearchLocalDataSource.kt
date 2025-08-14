package com.moscow.data_source.local

import com.moscow.local.entity.FavouriteGenreEntity
import kotlinx.coroutines.flow.Flow

interface SearchLocalDataSource {
    suspend fun getAllSearchHistory(): Flow<List<String>>
    suspend fun insertSearchHistory(searchTerm: String)
    suspend fun deleteSearchHistory(searchTerm: String)
    suspend fun deleteAllSearchHistory()
    fun getFavouriteGenres(): Flow<List<FavouriteGenreEntity>>
    suspend fun insertFavouriteGenre(genreId: Int)

}