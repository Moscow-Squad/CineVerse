package com.repository

import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.android.domain.repository.RecommendationsMoviesRepository
import com.data_source.remote.RecommendationsRemoteDataSource
import com.mapper.toDomain

class RecommendationRepositoryImpl(
    private val recommendationsRemoteDataSource: RecommendationsRemoteDataSource,
    ) : RecommendationsMoviesRepository {


    override suspend fun getMoviesRecommendations(
        id: Int,
        page: Int
    ): List<Movie> {
        val movies = recommendationsRemoteDataSource.getMoviesRecommendations(id, page)
       return movies.results?.mapNotNull { runCatching { it.toDomain() }.getOrNull() } ?: emptyList()

    }

    override suspend fun getSeriesRecommendations(
        id: Int,
        page: Int
    ): List<Series> {
        val series = recommendationsRemoteDataSource.getSeriesRecommendations(id, page)
       return series.results?.mapNotNull { runCatching { it.toDomain() }.getOrNull() } ?: emptyList()
    }
}