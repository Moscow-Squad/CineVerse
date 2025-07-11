package com.local

import com.local.dao.search.MovieDao
import com.local.dao.search.SearchHistoryDao
import com.local.entity.MovieEntity
import com.local.entity.SearchHistoryEntity
import com.repository.search.SearchLocalDateSource

class SearchLocalDateSourceImpl(
    private val searchHistoryDao: SearchHistoryDao,
    private val movieDao: MovieDao
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


}