package com.repository.details


import com.android.domain.model.CreditsDetails
import com.android.domain.model.Movie
import com.android.domain.model.MovieDetail
import com.android.domain.model.Review
import com.android.domain.model.SeriesDetail
import com.android.domain.repository.DetailsRepository
import com.mapper.toDomain
import com.remote.source.DetailsRemoteDataSource
import com.repository.mapper.toDomain

class DetailsRepositoryImpl(
    private val detailsRemoteDataSource: DetailsRemoteDataSource,
) : DetailsRepository {
    override suspend fun getMoviesDetail(movieId: Int): MovieDetail =
        detailsRemoteDataSource.getMovieDetails(movieId).toDomain()

    override suspend fun getSeriesDetail(seriesId: Int): SeriesDetail =
        detailsRemoteDataSource.getSeriesDetails(seriesId).toDomain()




    override suspend fun getCreditsDetails(id: Int): CreditsDetails {
        val response = detailsRemoteDataSource.getCredits(id)
        return response.toDomain()
    }


}
