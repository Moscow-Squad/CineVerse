package com.repository


import com.android.domain.model.Genre
import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.android.domain.repository.ExploreRepository
import com.data_source.remote.ExploreRemoteDataSource
import com.mapper.toDomain

class ExploreRepositoryImpl(
    private val exploreRemoteDataSource: ExploreRemoteDataSource,
) : ExploreRepository {

    override suspend fun getSeriesGenres(): List<Genre> {
        return exploreRemoteDataSource.getSeriesGenres().genres.map { it.toDomain() }
    }


    override suspend fun getMoviesGenres(): List<Genre> {
        return exploreRemoteDataSource.getMoviesGenres().genres.map { it.toDomain() }
    }


    override suspend fun getMovies(page: Int): List<Movie> {
        return exploreRemoteDataSource.getMovies(page = page).results?.map { it.toDomain() } ?: emptyList()
    }


    override suspend fun getSeries(page: Int): List<Series> {
        return exploreRemoteDataSource.getSeries(page).results?.map { it.toDomain() } ?: emptyList()
    }


    override suspend fun getMoviesByGenreId(genreId: Int, page: Int): List<Movie> {
        return exploreRemoteDataSource.getMoviesByGenreId(
            genreId,
            page
        ).results?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getSeriesByGenreId(genreId: Int, page: Int): List<Series> {
        return exploreRemoteDataSource.getSeriesByGenreId(
            genreId,
            page
        ).results?.map { it.toDomain() } ?: emptyList()
    }

}