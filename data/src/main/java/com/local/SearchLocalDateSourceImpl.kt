package com.local

import com.local.dao.search.ActorDao
import com.local.dao.search.MovieDao
import com.local.dao.search.SearchHistoryDao
import com.local.dao.search.SeriesDao
import com.local.entity.ActorEntity
import com.local.entity.MovieEntity
import com.local.entity.SearchHistoryEntity
import com.local.entity.SeriesEntity
import com.repository.explore.search.SearchLocalDateSource

class SearchLocalDateSourceImpl(
    private val searchHistoryDao: SearchHistoryDao,
    private val movieDao: MovieDao,
    private val actorDao: ActorDao,
    private val seriesDao: SeriesDao
): SearchLocalDateSource {
    override suspend fun getAllSearchHistory(): List<String> {
        return searchHistoryDao.getAllSearchHistory()
    }

    override suspend fun insertSearchHistory(searchTerm: String) {
        searchHistoryDao.insertSearchHistory(SearchHistoryEntity(searchTerm))
    }

    override suspend fun deleteSearchHistory(searchTerm: String) {
        searchHistoryDao.deleteSearchHistory(SearchHistoryEntity(searchTerm))
    }

    override suspend fun insertMovie(moviesEntity: List<MovieEntity>, searchTerm: String) {
        val updatedMovieEntity = moviesEntity.map { movie->movie.copy(searchTerm = searchTerm)}
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
}