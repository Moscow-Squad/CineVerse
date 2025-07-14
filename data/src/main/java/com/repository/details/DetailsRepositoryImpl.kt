package com.repository.details


import com.android.domain.model.MovieDetail
import com.android.domain.model.SeriesDetail
import com.android.domain.repository.DetailsRepository
import com.mapper.toDomain
import com.remote.source.DetailsRemoteDataSource

class DetailsRepositoryImpl(
    private val detailsRemoteDataSource: DetailsRemoteDataSource,
) : DetailsRepository {
    override suspend fun getMoviesDetail(movieId: Int): MovieDetail =
        detailsRemoteDataSource.getMovieDetails(movieId).toDomain()

    override suspend fun getSeriesDetail(seriesId: Int): SeriesDetail =
        detailsRemoteDataSource.getSeriesDetails(seriesId).toDomain()
}
