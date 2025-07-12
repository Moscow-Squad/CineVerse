package com.repository.search

import com.local.entity.ActorEntity
import com.local.entity.MovieEntity
import com.local.entity.SeriesEntity

interface SearchLocalDateSource {
    suspend fun getAllSearchHistory(): List<String>

    suspend fun insertSearchHistory(searchTerm: String)

    suspend fun deleteSearchHistory(searchTerm: String)

    suspend fun insertMovie(moviesEntity: List<MovieEntity>, searchTerm: String)

    suspend fun getMoviesBySearchTerm(searchTerm: String): List<MovieEntity>

    suspend fun insertActors(actors: List<ActorEntity>, searchTerm: String)
    suspend fun getActorsBySearchTerm(searchTerm: String): List<ActorEntity>

    suspend fun insertSeries(series: List<SeriesEntity>, searchTerm: String)
    suspend fun getSeriesBySearchTerm(searchTerm: String): List<SeriesEntity>

}