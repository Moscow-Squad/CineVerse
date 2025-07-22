package com.repository.details


import com.android.domain.model.CreditsDetails
import com.android.domain.model.details.ListOfSeries
import com.android.domain.model.details.MovieDetail
import com.android.domain.model.details.Season
import com.android.domain.model.details.SeriesDetail
import com.android.domain.repository.DetailsRepository
import com.data_source.local.DetailsLocalDataSource
import com.mapper.toDomain
import com.remote.dto.review.RatingRequestDto
import com.remote.data_source.DetailsRemoteDataSourceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DetailsRepositoryImpl(
    private val detailsRemoteDataSourceImpl: DetailsRemoteDataSourceImpl,
    private val detailsLocalDataSource: DetailsLocalDataSource,
) : DetailsRepository {
    override suspend fun getMoviesDetail(movieId: Int): MovieDetail {
        val res = detailsRemoteDataSourceImpl.getMovieDetails(movieId)
        res.genres?.forEach { detailsLocalDataSource.insertFavouriteGenre(it.id) }
        return res.toDomain()
    }

    override suspend fun getSeriesDetail(seriesId: Int): SeriesDetail {
        val res = detailsRemoteDataSourceImpl.getSeriesDetails(seriesId)
        res.genres?.forEach { detailsLocalDataSource.insertFavouriteGenre(it.id) }
        return res.toDomain()
    }

    override suspend fun getCreditsDetails(id: Int): CreditsDetails {
        val response = detailsRemoteDataSourceImpl.getCredits(id)
        return response.toDomain()
    }


    override suspend fun getLatestSeasons(): List<Season> {
        val response = detailsRemoteDataSourceImpl.getLatestSeasons()
        return response.seasons?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getListOfSeries(id: Int, page: Int): List<ListOfSeries> {
        val response = detailsRemoteDataSourceImpl.getListOfSeries(id, page)
        return listOf(response.toDomain())
    }


    override suspend fun rateMovie(
        rating: Float,
        movieId: Int
    ): Flow<Unit> =
        flow {
            val request = RatingRequestDto(value = rating)
            emit(detailsRemoteDataSourceImpl.rateMovie(rating = request, movieId = movieId))
        }.flowOn(Dispatchers.IO)


    override suspend fun rateSeries(
        rating: Float,
        seriesId: Int
    ): Flow<Unit> =
        flow {
            val request = RatingRequestDto(value = rating)
            emit(detailsRemoteDataSourceImpl.rateSeries(rating = request, seriesId = seriesId))
        }.flowOn(Dispatchers.IO)


}