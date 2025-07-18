package com.android.domain.repository

import com.android.domain.model.Movie

interface RecommendationsMoviesRepository {
    suspend fun getRecommendationsMovies(id:Int,page: Int) : List<Movie>

}