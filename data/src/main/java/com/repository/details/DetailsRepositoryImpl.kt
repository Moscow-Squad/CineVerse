package com.repository.details


import com.android.domain.model.Review
import com.android.domain.model.details.ListOfSeries
import com.android.domain.model.details.MovieDetail
import com.android.domain.model.details.SeriesDetail
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


    override suspend fun getReviewsPage(id:Int, page: Int, isMovie: Boolean): List<Review> {
        val response = detailsRemoteDataSource.getReviews(id, page, isMovie)
        return response.results.orEmpty().mapNotNull { it?.toDomain() }
    }

    override suspend fun getLatestSeasons(): List<SeriesDetail> {
        val response = detailsRemoteDataSource.getLatestSeasons()
        return response.map { it.toDomain() }
    }

    override suspend fun getListOfSeries(id: Int, page: Int): List<ListOfSeries> {
        val response = detailsRemoteDataSource.getListOfSeries(id, page)
        return listOf(response.toDomain())
    }
}
