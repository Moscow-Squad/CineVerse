package com.moscow.repository


import com.moscow.domain.repository.HomeRepository
import com.data_source.remote.HomeRemoteDataSource
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series
import com.moscow.mapper.toDomain
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
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