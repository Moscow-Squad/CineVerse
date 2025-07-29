package com.moscow.local

import com.moscow.data_source.local.SearchLocalDataSource
import com.moscow.local.dao.search.ActorDao
import com.moscow.local.dao.search.FavouriteGenreDao
import com.moscow.local.dao.search.MovieDao
import com.moscow.local.dao.search.SearchHistoryDao
import com.moscow.local.dao.search.SeriesDao
import com.moscow.local.entity.ActorEntity
import com.moscow.local.entity.FavouriteGenreEntity
import com.moscow.local.entity.MovieEntity
import com.moscow.local.entity.SearchHistoryEntity
import com.moscow.local.entity.SeriesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.collections.map

class SearchLocalDataSourceImpl  @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao,
    private val movieDao: MovieDao,
    private val actorDao: ActorDao,
    private val seriesDao: SeriesDao,
    private val favouriteGenreDao: FavouriteGenreDao
) : SearchLocalDataSource {

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
        movieDao.insertMovies(updatedMovieEntity)
    }

    override suspend fun getMoviesBySearchTerm(searchTerm: String): List<MovieEntity> {
        return movieDao.getMoviesBySearchTerm(searchTerm)
    }

    override suspend fun insertActors(actors: List<ActorEntity>, searchTerm: String) {
        val updatedActors = actors.map { it.copy(searchTerm = searchTerm) }
        actorDao.insertActors(updatedActors)
    }

    override suspend fun getActorsBySearchTerm(searchTerm: String): List<ActorEntity> {
        return actorDao.getActorsBySearchTerm(searchTerm)
    }

    override suspend fun insertSeries(series: List<SeriesEntity>, searchTerm: String) {
        val updatedSeries = series.map { it.copy(searchTerm = searchTerm) }
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
