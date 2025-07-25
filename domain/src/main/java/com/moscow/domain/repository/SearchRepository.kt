package com.moscow.domain.repository

import com.moscow.domain.model.Actor
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getLocalMoviesBySearchTerm(searchTerm: String): List<Movie>
    suspend fun insertMovie(movies: List<Movie>, searchTerm: String)
    suspend fun insertActors(actors: List<Actor>, searchTerm: String)
    suspend fun insertSeries(series: List<Series>, searchTerm: String)
    suspend fun getLocalSuggestions(): Flow<List<String>>
    suspend fun deleteSearchHistory(searchTerm: String)
    suspend fun getRemoteSuggestions(keyWord: String, page: Int): List<String>
    suspend fun searchMovie(query: String, page: Int, isHistory: Boolean = false): Flow<List<Movie>>
    suspend fun searchSeries(
        query: String,
        page: Int,
        isHistory: Boolean = false
    ): Flow<List<Series>>

    suspend fun searchActor(query: String, page: Int, isHistory: Boolean = false): Flow<List<Actor>>
    suspend fun cacheSearchQuery(query: String)
    suspend fun clearSearchHistory()
}