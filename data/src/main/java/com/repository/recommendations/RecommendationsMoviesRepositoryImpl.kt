package com.repository.recommendations

import com.android.domain.model.Movie
import com.android.domain.repository.RecommendationsMoviesRepository
import com.mapper.toDomain
import com.remote.source.RecommendationsMoviesRemoteDataSource
import com.utils.BaseRepository


class RecommendationsMoviesRepositoryImpl(
    private val recommendationsMoviesRemoteDataSource: RecommendationsMoviesRemoteDataSource,

    ) : RecommendationsMoviesRepository , BaseRepository(){
    override suspend fun getRecommendationsMovies(id: Int, page: Int): List<Movie> =
        tryToExecute {
            val movies = recommendationsMoviesRemoteDataSource.getRecommendations(id, page)
            movies.mapNotNull { runCatching { it.toDomain() }.getOrNull() }

        }
}