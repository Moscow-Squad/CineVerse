package com.android.domain

import com.android.domain.model.Movie

interface SearchRepository {
    suspend fun getMoviesBySearchTerm(searchTerm: String): List<Movie>
    suspend fun insertMovie(movies: List<Movie>, searchTerm: String)

    suspend fun getSearchHistory(): List<String>
    suspend fun deleteSearchHistory(searchTerm: String)

}