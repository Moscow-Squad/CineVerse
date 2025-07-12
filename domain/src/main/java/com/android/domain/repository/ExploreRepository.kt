package com.android.domain.repository

import com.android.domain.model.Genre
import com.android.domain.model.Movie
import com.android.domain.model.Series
import kotlinx.coroutines.flow.Flow


interface ExploreRepository {
    suspend fun getSeriesGenres(): Flow<List<Genre>>
    suspend fun getMoviesGenres(): Flow<List<Genre>>
    suspend fun getMoviesByGenreId(genreId: Int): Flow<List<Movie>>
    suspend fun getSeriesByGenreId(genreId: Int): Flow<List<Series>>

}