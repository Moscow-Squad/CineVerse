package com.repository.details

import com.local.entity.FavouriteGenreEntity
import kotlinx.coroutines.flow.Flow

interface DetailsLocalDataSource {
    suspend fun insertFavouriteGenre(genreId: Int)
    suspend fun incrementGenreCount(genreId: Int)
    suspend fun getFavouriteGenres(): Flow<List<FavouriteGenreEntity>>
}