package com.moscow.domain.repository

import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series


interface HomeRepository {
    suspend fun getTrendingMovies(time:String?): List<Movie>
    suspend fun getUpComingMovies(page: Int): List<Movie>
    suspend fun getRecentlyReleasedMovies(page: Int): List<Movie>
    suspend fun getMatchYourVibeMovies(genreId: Int, page: Int): List<Movie>
    suspend fun getTopRatedTVSeries(page: Int): List<Series>

}