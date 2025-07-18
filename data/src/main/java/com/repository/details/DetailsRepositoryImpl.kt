package com.repository.details


import com.android.domain.model.MovieDetail
import com.android.domain.model.Review
import com.android.domain.model.SeriesDetail
import com.android.domain.repository.DetailsRepository
import com.mapper.toDomain
import com.remote.dto.review.RatingRequestDto
import com.remote.source.DetailsRemoteDataSource
import com.repository.mapper.toDomain
import com.utils.BaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DetailsRepositoryImpl(
    private val detailsRemoteDataSource: DetailsRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : DetailsRepository, BaseRepository() {
    override suspend fun getMoviesDetail(movieId: Int): MovieDetail =
        detailsRemoteDataSource.getMovieDetails(movieId).toDomain()

    override suspend fun getSeriesDetail(seriesId: Int): SeriesDetail =
        detailsRemoteDataSource.getSeriesDetails(seriesId).toDomain()


    override suspend fun getReviewsPage(id: Int, page: Int, isMovie: Boolean): List<Review> {
        val response = detailsRemoteDataSource.getReviews(id, page, isMovie)
        return response.results.orEmpty().mapNotNull { it?.toDomain() }
    }

    override suspend fun rateMovie(
        rating: Float,
        movieId: Int
    ): Flow<Unit> = tryToExecute {
        flow {
            val request = RatingRequestDto(value = rating)
            emit(detailsRemoteDataSource.rateMovie(rating = request, movieId = movieId))
        }.flowOn(ioDispatcher)
    }

    override suspend fun rateSeries(
        rating: Float,
        seriesId: Int
    ): Flow<Unit> = tryToExecute {
        flow {
            val request = RatingRequestDto(value = rating)
            emit(detailsRemoteDataSource.rateSeries(rating = request, seriesId = seriesId))
        }.flowOn(ioDispatcher)
    }

}
