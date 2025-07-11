package com.android.domain.repository

import com.android.domain.model.Movie
import com.android.domain.model.Suggestion
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getMoviesBySearchTerm(searchTerm: String): List<Movie>
    suspend fun insertMovie(movies: List<Movie>, searchTerm: String)
    suspend fun getLocalSuggestions(): List<String>
    suspend fun deleteSearchHistory(searchTerm: String)
    fun getRemoteSuggestions(keyWord:String,page:Int): Flow<List<Suggestion>>
}