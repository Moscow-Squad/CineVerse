package com.android.domain.repository

import com.android.domain.FeaturedCollection
import com.android.domain.model.Movie
import com.android.domain.model.MoviesCollection
import com.android.domain.model.Series

interface HomeRepository {
    suspend fun getRecentlyReleasedMovies(): List<Movie>
    suspend fun getUpcomingMovies(): List<Movie>
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getTopRatedTvShows(): List<Series>
    suspend fun getRecommendationMovies(): List<Movie>
    suspend fun getRecentViews(): List<Movie>
    suspend fun getCollections(): List<MoviesCollection>
    suspend fun getFeaturedCollections(): List<FeaturedCollection>
}