package com.repository

import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.android.domain.repository.HomeRepository
import com.data_source.remote.HomeRemoteDataSource
import com.moscow.mapper.toDomain

class HomeRepositoryImpl(
    private val homeRemoteDataSource: HomeRemoteDataSource
) : HomeRepository {
    override suspend fun getTrendingMovies(time: String?): List<Movie> =
        homeRemoteDataSource.getTrendingMovies(time).results?.map { it.toDomain() } ?: emptyList()

    override suspend fun getUpComingMovies(page: Int): List<Movie> =
        homeRemoteDataSource.getUpComingMovies(page).results?.map { it.toDomain() } ?: emptyList()

    override suspend fun getRecentlyReleasedMovies(page: Int): List<Movie> =
        homeRemoteDataSource.getRecentlyReleasedMovies(page).results?.map { it.toDomain() } ?: emptyList()

    override suspend fun getMatchYourVibeMovies(
        genreId: Int, page: Int
    ): List<Movie> =
        homeRemoteDataSource.getMatchYourVibeMovies(genreId, page).results?.map { it.toDomain() }
            ?: emptyList()

    override suspend fun getTopRatedTVSeries(page: Int): List<Series> =
        homeRemoteDataSource.getTopRatedTVSeries(page).results?.map { it.toDomain() } ?: emptyList()

}