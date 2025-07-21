package com.repository.explore


import com.android.domain.repository.ExploreRepository
import com.mapper.toDomain
import com.remote.source.ExploreRemoteDataSource
import com.utils.BaseRepository

class ExploreRepositoryImpl(
    private val exploreRemoteDataSource: ExploreRemoteDataSource,
) : ExploreRepository, BaseRepository() {

    override suspend fun getSeriesGenres() =
      tryToExecute {
          exploreRemoteDataSource.getSeriesGenres()
      }.genres.map { it.toDomain() }


    override suspend fun getMoviesGenres() = tryToExecute {
        exploreRemoteDataSource.getMoviesGenres()
    }.genres.map { it.toDomain() }


    override suspend fun geMovies(page:Int) = tryToExecute {
        exploreRemoteDataSource.getMovies(page = page)
    }.results.map { it.toDomain() }


    override suspend fun getSeries(page: Int) = tryToExecute {
        exploreRemoteDataSource.getSeries(page)
    }.results.map { it.toDomain() }


    override suspend fun getMoviesByGenreId(genreId: Int, page:Int) = tryToExecute {
        exploreRemoteDataSource.getMoviesByGenreId(genreId,page)
    }.results.map { it.toDomain() }

    override suspend fun getSeriesByGenreId(genreId: Int, page: Int) = tryToExecute {
        exploreRemoteDataSource.getSeriesByGenreId(genreId , page)
    }.results.map { it.toDomain() }

}