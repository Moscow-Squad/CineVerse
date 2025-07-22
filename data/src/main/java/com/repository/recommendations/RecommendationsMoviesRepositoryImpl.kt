package com.repository.recommendations

import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.android.domain.repository.RecommendationsMoviesRepository
import com.mapper.toDomain
import com.remote.source.RecommendationsMoviesRemoteDataSource
import com.repository.mapper.toDomain
import com.utils.BaseRepository


class RecommendationsMoviesRepositoryImpl(
    private val recommendationsMoviesRemoteDataSource: RecommendationsMoviesRemoteDataSource,

    ) : RecommendationsMoviesRepository , BaseRepository(){
    override suspend fun getRecommendationsMovies(id: Int, page: Int): List<Movie> =
        tryToExecute {
            val movies = recommendationsMoviesRemoteDataSource.getRecommendations(id, page)
            movies.mapNotNull { runCatching { it.toDomain() }.getOrNull() }
        }

    override suspend fun getSeriesRecommendations(id: Int, page: Int): List<Series> =
        tryToExecute {
            val movies = recommendationsMoviesRemoteDataSource.getSeriesRecommendations(id, page)
            movies.mapNotNull { runCatching { it.toDomain() }.getOrNull() }
        }
}