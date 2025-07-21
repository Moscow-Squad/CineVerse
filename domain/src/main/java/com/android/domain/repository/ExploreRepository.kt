package com.android.domain.repository

import com.android.domain.model.Genre
import com.android.domain.model.Movie
import com.android.domain.model.Series
import kotlinx.coroutines.flow.Flow

interface ExploreRepository {
    suspend fun getSeriesGenres():List<Genre>
    suspend fun getMoviesGenres(): List<Genre>
    suspend fun geMovies(page:Int): List<Movie>
    suspend fun getSeries(page:Int): List<Series>
    suspend fun getMoviesByGenreId(genreId: Int,page:Int): List<Movie>
    suspend fun getSeriesByGenreId(genreId: Int,page:Int): List<Series>
}