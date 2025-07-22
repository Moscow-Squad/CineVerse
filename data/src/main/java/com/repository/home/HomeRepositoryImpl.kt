package com.repository.home

import com.android.domain.FeaturedCollection
import com.android.domain.model.Movie
import com.android.domain.model.MoviesCollection
import com.android.domain.model.Series
import com.android.domain.repository.HomeRepository

class HomeRepositoryImpl(
    private val dataSource: HomeDataSource
) : HomeRepository {
    override suspend fun getRecentlyReleasedMovies(): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getUpcomingMovies(): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getTopRatedMovies(): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getTopRatedTvShows(): List<Series> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecommendationMovies(): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecentViews(): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getCollections(): List<MoviesCollection> {
        TODO("Not yet implemented")
    }

    override suspend fun getFeaturedCollections(): List<FeaturedCollection> {
        TODO("Not yet implemented")
    }
}