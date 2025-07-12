package com.android.domain.repository

import com.android.domain.model.Actor
import com.android.domain.model.Movie
import com.android.domain.model.MultiSearch
import com.android.domain.model.Series
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getLocalMoviesBySearchTerm(searchTerm: String): List<Movie>
    suspend fun insertMovie(movies: List<Movie>, searchTerm: String)
    suspend fun getLocalSuggestions(): List<String>
    suspend fun deleteSearchHistory(searchTerm: String)
    suspend fun getRemoteSuggestions(keyWord: String, page: Int): Flow<List<String>>
    suspend fun searchMulti(query: String): Flow<List<MultiSearch>>
    suspend fun searchMovie(query: String): Flow<List<Movie>>
    suspend fun searchSeries(query: String): Flow<List<Series>>
    suspend fun searchActor(query: String): Flow<List<Actor>>
}