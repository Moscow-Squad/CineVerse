package com.repository.recommendations

import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.android.domain.repository.RecommendationsMoviesRepository
import com.mapper.toDomain
import com.remote.data_source.RecommendationsRemoteDataSourceImpl

class RecommendationRepositoryImpl(
    private val recommendationsRemoteDataSourceImpl: RecommendationsRemoteDataSourceImpl,
    ) : RecommendationsMoviesRepository {


    override suspend fun getMoviesRecommendations(
        id: Int,
        page: Int
    ): List<Movie> {
        val movies = recommendationsRemoteDataSourceImpl.getMoviesRecommendations(id, page)
       return movies.results.mapNotNull { runCatching { it.toDomain() }.getOrNull() }

    }

    override suspend fun getSeriesRecommendations(
        id: Int,
        page: Int
    ): List<Series> {
        val series = recommendationsRemoteDataSourceImpl.getSeriesRecommendations(id, page)
       return series.results.mapNotNull { runCatching { it.toDomain() }.getOrNull() }
    }
}