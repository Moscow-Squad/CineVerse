package com.repository.details


import com.android.domain.model.CreditsDetails
import com.android.domain.model.Movie
import com.android.domain.model.Review
import com.android.domain.model.details.ListOfSeries
import com.android.domain.model.details.MovieDetail
import com.android.domain.model.details.SeriesDetail
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
    private val detailsLocalDataSource: DetailsLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : DetailsRepository, BaseRepository() {
    override suspend fun getMoviesDetail(movieId: Int): MovieDetail {
        val res = detailsRemoteDataSource.getMovieDetails(movieId)
        res.genres?.forEach { detailsLocalDataSource.insertFavouriteGenre(it.id) }
        return res.toDomain()
    }

    override suspend fun getSeriesDetail(seriesId: Int): SeriesDetail {
        val res = detailsRemoteDataSource.getSeriesDetails(seriesId)
        res.genres.forEach { detailsLocalDataSource.insertFavouriteGenre(it.id) }
        return res.toDomain()
    }

    override suspend fun getCreditsDetails(id: Int): CreditsDetails {
        val response = detailsRemoteDataSource.getCredits(id)
        return response.toDomain()
    }


    override suspend fun getRecommendations(id: Int,page:Int): List<Movie> {
        val response = detailsRemoteDataSource.getRecommendations(id,page)
        return response.map { it.toDomain() }
    }


    override suspend fun getLatestSeasons(): List<SeriesDetail> {
        val response = detailsRemoteDataSource.getLatestSeasons()
        return response.map { it.toDomain() }
    }

    override suspend fun getListOfSeries(id: Int, page: Int): List<ListOfSeries> {
        val response = detailsRemoteDataSource.getListOfSeries(id, page)
        return listOf(response.toDomain())
    }

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