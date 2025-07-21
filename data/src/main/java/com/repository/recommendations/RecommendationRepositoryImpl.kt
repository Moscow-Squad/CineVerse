package com.repository.recommendations

import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.android.domain.repository.RecommendationsMoviesRepository
import com.mapper.toDomain
import com.remote.source.RecommendationsRemoteDataSource
import com.utils.BaseRepository

class RecommendationRepositoryImpl(
    private val recommendationsRemoteDataSource: RecommendationsRemoteDataSource,
    ) : RecommendationsMoviesRepository , BaseRepository(){


    override suspend fun getMoviesRecommendations(
        id: Int,
        page: Int
    ): List<Movie> =  tryToExecute {
        val movies = recommendationsRemoteDataSource.getMoviesRecommendations(id, page)
        movies.results.mapNotNull { runCatching { it.toDomain() }.getOrNull() }

    }

    override suspend fun getSeriesRecommendations(
        id: Int,
        page: Int
    ): List<Series> =  tryToExecute {
        val series = recommendationsRemoteDataSource.getSeriesRecommendations(id, page)
        series.results.mapNotNull { runCatching { it.toDomain() }.getOrNull() }
    }
}