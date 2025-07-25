package com.moscow.local

import com.moscow.data_source.local.DetailsLocalDataSource
import com.moscow.local.dao.search.FavouriteGenreDao
import com.moscow.local.entity.FavouriteGenreEntity
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
