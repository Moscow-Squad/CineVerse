package com.moscow.data_source.local

import com.moscow.local.entity.ActorEntity
import com.moscow.local.entity.FavouriteGenreEntity
import com.moscow.local.entity.MovieEntity
import com.moscow.local.entity.SeriesEntity
import kotlinx.coroutines.flow.Flow

interface SearchLocalDataSource {
    suspend fun getAllSearchHistory(): Flow<List<String>>

    suspend fun insertSearchHistory(searchTerm: String)

    suspend fun deleteSearchHistory(searchTerm: String)
    suspend fun deleteAllSearchHistory()

    suspend fun insertMovie(moviesEntity: List<MovieEntity>, searchTerm: String)

    suspend fun getMoviesBySearchTerm(searchTerm: String): List<MovieEntity>

    suspend fun insertActors(actors: List<ActorEntity>, searchTerm: String)

    suspend fun getActorsBySearchTerm(searchTerm: String): List<ActorEntity>

    suspend fun insertSeries(series: List<SeriesEntity>, searchTerm: String)

    suspend fun getSeriesBySearchTerm(searchTerm: String): List<SeriesEntity>

    fun getFavouriteGenres(): Flow<List<FavouriteGenreEntity>>

    suspend fun insertFavouriteGenre(genreId: Int)

}