package com.local

import com.local.dao.search.FavouriteGenreDao
import com.local.entity.FavouriteGenreEntity
import com.data_source.local.DetailsLocalDataSource
import kotlinx.coroutines.flow.Flow

class DetailsLocalDataSourceImpl(
    private val favouriteGenreDao: FavouriteGenreDao
) : DetailsLocalDataSource {

    override suspend fun insertFavouriteGenre(genreId: Int) {
        val existingGenre = favouriteGenreDao.getGenreById(genreId)
        if (existingGenre != null) {
            favouriteGenreDao.incrementGenreCount(genreId)
        } else {
            favouriteGenreDao.insertOrUpdateFavouriteGenre(
                FavouriteGenreEntity(genreId = genreId, count = 1)
            )
        }
    }

    override suspend fun incrementGenreCount(genreId: Int) {
        insertFavouriteGenre(genreId)
    }

    override suspend fun getFavouriteGenres(): Flow<List<FavouriteGenreEntity>> {
        return favouriteGenreDao.getFavouriteGenres()
    }
}