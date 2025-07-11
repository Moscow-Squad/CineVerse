package com.repository.explore.search

import com.local.entity.MovieEntity

interface SearchLocalDateSource {
    suspend fun getAllSearchHistory(): List<String>

    suspend fun insertSearchHistory(searchTerm: String)

    suspend fun deleteSearchHistory(searchTerm: String)

    suspend fun insertMovie(moviesEntity: List<MovieEntity>, searchTerm: String)

    suspend fun getMoviesBySearchTerm(searchTerm: String): List<MovieEntity>

}