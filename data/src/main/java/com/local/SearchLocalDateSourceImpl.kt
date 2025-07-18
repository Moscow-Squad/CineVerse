package com.local

import android.util.Log
import com.local.dao.search.ActorDao
import com.local.dao.search.FavouriteGenreDao
import com.local.dao.search.MovieDao
import com.local.dao.search.SearchHistoryDao
import com.local.dao.search.SeriesDao
import com.local.entity.ActorEntity
import com.local.entity.FavouriteGenreEntity
import com.local.entity.MovieEntity
import com.local.entity.SearchHistoryEntity
import com.local.entity.SeriesEntity
import com.repository.explore.search.SearchLocalDateSource
import kotlinx.coroutines.flow.Flow

class SearchLocalDateSourceImpl(
    private val searchHistoryDao: SearchHistoryDao,
    private val movieDao: MovieDao,
    private val actorDao: ActorDao,
    private val seriesDao: SeriesDao,
    private val favouriteGenreDao: FavouriteGenreDao
) : SearchLocalDateSource {

    override suspend fun getAllSearchHistory(): Flow<List<String>> =
        searchHistoryDao.getAllSearchHistory()

    override suspend fun insertSearchHistory(searchTerm: String) {
        searchHistoryDao.insertSearchHistory(SearchHistoryEntity(searchTerm))
    }

    override suspend fun deleteSearchHistory(searchTerm: String) {
        searchHistoryDao.deleteSearchHistory(SearchHistoryEntity(searchTerm))
    }

    override suspend fun deleteAllSearchHistory() {
        searchHistoryDao.deleteAllSearchHistory()
    }

    override suspend fun insertMovie(moviesEntity: List<MovieEntity>, searchTerm: String) {
        val updatedMovieEntity = moviesEntity.map { movie -> movie.copy(searchTerm = searchTerm) }
        Log.d("SearchLocalDateSourceImpl", "insertMovie: $updatedMovieEntity")
        movieDao.insertMovies(updatedMovieEntity)
    }

    override suspend fun getMoviesBySearchTerm(searchTerm: String): List<MovieEntity> {
        return movieDao.getMoviesBySearchTerm(searchTerm)
    }

    override suspend fun insertActors(actors: List<ActorEntity>, searchTerm: String) {
        val updatedActors = actors.map { it.copy(searchTerm = searchTerm) }
        Log.d("SearchLocalDateSourceImpl", "insertActors: $updatedActors")
        actorDao.insertActors(updatedActors)
    }

    override suspend fun getActorsBySearchTerm(searchTerm: String): List<ActorEntity> {
        return actorDao.getActorsBySearchTerm(searchTerm)
    }

    override suspend fun insertSeries(series: List<SeriesEntity>, searchTerm: String) {
        val updatedSeries = series.map { it.copy(searchTerm = searchTerm) }
        Log.d("SearchLocalDateSourceImpl", "insertSeries: $updatedSeries")
        seriesDao.insertSeries(updatedSeries)
    }

    override suspend fun getSeriesBySearchTerm(searchTerm: String): List<SeriesEntity> {
        return seriesDao.getSeriesBySearchTerm(searchTerm)
    }

    override fun getFavouriteGenres(): Flow<List<FavouriteGenreEntity>> {
        return favouriteGenreDao.getFavouriteGenres()
    }

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
}