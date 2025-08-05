package com.moscow.domain.repository

import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series


interface HomeRepository {
    suspend fun getTrendingMovies(time:String?, forceRefresh: Boolean = false): List<Movie>
    suspend fun getUpComingMovies(page: Int, forceRefresh: Boolean = false): List<Movie>
    suspend fun getRecentlyReleasedMovies(page: Int, forceRefresh: Boolean = false): List<Movie>
    suspend fun getMatchYourVibeMovies(genreId: Int, page: Int, forceRefresh: Boolean = false): List<Movie>
    suspend fun getTopRatedTVSeries(page: Int, forceRefresh: Boolean = false): List<Series>
}