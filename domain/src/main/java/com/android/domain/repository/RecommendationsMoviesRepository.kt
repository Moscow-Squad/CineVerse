package com.android.domain.repository

import com.android.domain.model.Movie
import com.android.domain.model.Series

interface RecommendationsMoviesRepository {
    suspend fun getRecommendationsMovies(id:Int,page: Int) : List<Movie>
    suspend fun getSeriesRecommendations(id: Int,page:Int): List<Series>
}