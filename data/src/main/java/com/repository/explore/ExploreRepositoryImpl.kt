package com.repository.explore


import com.android.domain.model.Genre
import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.android.domain.repository.ExploreRepository
import com.mapper.toDomain
import com.remote.data_source.ExploreRemoteDataSourceImpl

class ExploreRepositoryImpl(
    private val exploreRemoteDataSourceImpl: ExploreRemoteDataSourceImpl,
) : ExploreRepository {

    override suspend fun getSeriesGenres(): List<Genre> {
        return exploreRemoteDataSourceImpl.getSeriesGenres().genres.map { it.toDomain() }
    }


    override suspend fun getMoviesGenres(): List<Genre> {
        return exploreRemoteDataSourceImpl.getMoviesGenres().genres.map { it.toDomain() }
    }


    override suspend fun getMovies(page: Int): List<Movie> {
        return exploreRemoteDataSourceImpl.getMovies(page = page).results.map { it.toDomain() }
    }


    override suspend fun getSeries(page: Int): List<Series> {
        return exploreRemoteDataSourceImpl.getSeries(page).results.map { it.toDomain() }
    }


    override suspend fun getMoviesByGenreId(genreId: Int, page: Int): List<Movie> {
        return exploreRemoteDataSourceImpl.getMoviesByGenreId(
            genreId,
            page
        ).results.map { it.toDomain() }
    }

    override suspend fun getSeriesByGenreId(genreId: Int, page: Int): List<Series> {
        return exploreRemoteDataSourceImpl.getSeriesByGenreId(
            genreId,
            page
        ).results.map { it.toDomain() }
    }

}