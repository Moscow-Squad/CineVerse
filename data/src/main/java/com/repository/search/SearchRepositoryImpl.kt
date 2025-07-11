package com.repository.search

import androidx.room.Transaction
import com.android.domain.SearchRepository
import com.android.domain.model.Movie
import com.repository.mapper.toDomain
import com.repository.mapper.toEntity
import com.utils.BaseRepository

class SearchRepositoryImpl(
    private val searchLocalDateSource: SearchLocalDateSource,
) : SearchRepository, BaseRepository(
) {
    override suspend fun getMoviesBySearchTerm(searchTerm: String): List<Movie> {
        return searchLocalDateSource.getMoviesBySearchTerm(searchTerm).toDomain()
    }

    @Transaction
    override suspend fun insertMovie(movies: List<Movie>, searchTerm: String) {
        searchLocalDateSource.insertSearchHistory(searchTerm)
        searchLocalDateSource.insertMovie(movies.toEntity(searchTerm), searchTerm)
    }
    override suspend fun getSearchHistory(): List<String> {
        return searchLocalDateSource.getAllSearchHistory()
    }

    override suspend fun deleteSearchHistory(searchTerm: String) {
        searchLocalDateSource.deleteSearchHistory(searchTerm)
    }

}