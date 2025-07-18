package com.repository.details


import com.android.domain.model.MovieDetail
import com.android.domain.model.Review
import com.android.domain.model.SeriesDetail
import com.android.domain.repository.DetailsRepository
import com.mapper.toDomain
import com.remote.source.DetailsRemoteDataSource
import com.repository.mapper.toDomain

class DetailsRepositoryImpl(
    private val detailsRemoteDataSource: DetailsRemoteDataSource,
    private val detailsLocalDataSource: DetailsLocalDataSource
) : DetailsRepository {
    override suspend fun getMoviesDetail(movieId: Int): MovieDetail {
        val res = detailsRemoteDataSource.getMovieDetails(movieId)
        res.genres.forEach { detailsLocalDataSource.insertFavouriteGenre(it.id) }
        return res.toDomain()
    }

    override suspend fun getSeriesDetail(seriesId: Int): SeriesDetail {
        val res = detailsRemoteDataSource.getSeriesDetails(seriesId)
        res.genres.forEach { detailsLocalDataSource.insertFavouriteGenre(it.id) }
        return res.toDomain()
    }

    override suspend fun getReviewsPage(id:Int, page: Int, isMovie: Boolean): List<Review> {
        val response = detailsRemoteDataSource.getReviews(id, page, isMovie)
        return response.results.orEmpty().mapNotNull { it?.toDomain() }
    }
}
