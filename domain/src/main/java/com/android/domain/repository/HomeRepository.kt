package com.android.domain.repository

import com.android.domain.model.Movie
import com.android.domain.model.Series

interface HomeRepository {
    suspend fun getUpComingMovies(page: Int): List<Movie>
    suspend fun getRecentlyReleasedMovies(page: Int): List<Movie>
    suspend fun getMatchYourVibeMovies(genreId: Int, page: Int): List<Movie>
    suspend fun getTopRatedTVSeries(page: Int): List<Series>

}