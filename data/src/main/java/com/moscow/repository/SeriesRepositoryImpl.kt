package com.moscow.repository

import com.moscow.data_source.local.DetailsLocalDataSource
import com.moscow.data_source.remote.SeriesRemoteDataSource
import com.moscow.domain.model.CreditsInfo
import com.moscow.domain.model.Review
import com.moscow.domain.model.Series
import com.moscow.domain.repository.SeriesRepository
import com.moscow.domain.usecase.rating.GetRatedSeriesUseCase
import com.moscow.mapper.toDomain
import com.moscow.mapper.toOutputResult
import com.moscow.mapper.toSeries
import com.moscow.remote.dto.rating.request.RatingRequestDto
import com.moscow.utils.HomeCacheHelper
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val homeCacheHelper: HomeCacheHelper,
    private val seriesRemoteDataSource: SeriesRemoteDataSource,
    private val detailsLocalDataSource: DetailsLocalDataSource
) : SeriesRepository {
    override suspend fun getPopularSeries(page: Int): List<Series> =
        seriesRemoteDataSource.getPopularSeries(page = page)
            .results
            ?.map { it.toDomain() } ?: emptyList()

    override suspend fun getSeriesDetail(id: Int): Series {
        val trailer = seriesRemoteDataSource
            .getSeriesTrailers(id)
            .trailers
            .firstOrNull { it.key != null }
            ?.key ?: ""
        val seriesDetails = seriesRemoteDataSource.getSeriesDetails(id)
        seriesDetails.genres.forEach { detailsLocalDataSource.insertFavouriteGenre(it.id) }
        return seriesDetails.toDomain(trailer)
    }

    override suspend fun rateSeries(
        id: Int,
        rating: Float
    ) {
        seriesRemoteDataSource.rateSeries(
            id = id,
            rating = RatingRequestDto(value = rating)
        )
    }

    override suspend fun deleteRatingSeries(seriesId: Int) {
        seriesRemoteDataSource.deleteRatingSeries(seriesId)
    }

    override suspend fun getRatedSeries(
        userId: Int,
        page: Int
    ): List<GetRatedSeriesUseCase.RatedSeriesResult> =
        seriesRemoteDataSource.getRatedSeries(
            userId = userId,
            page = page
        ).results?.mapNotNull { it.toOutputResult() } ?: emptyList()

    override suspend fun getUserRatingForSeries(seriesId: Int): Int =
        seriesRemoteDataSource.getUserRatingForSeries(seriesId = seriesId)
            .userRating ?: 0

    override suspend fun getLatestSeasons(): List<Series.Season> =
        seriesRemoteDataSource.getLatestSeasons()
            .seasons
            .map { it.toDomain() }

    override suspend fun getListOfSeries(id: Int, page: Int): List<Series> =
        seriesRemoteDataSource.getListOfSeries(
            id = id,
            page = page
        ).results?.map { it.toDomain() } ?: emptyList()


    override suspend fun getSeriesRecommendations(
        id: Int,
        page: Int
    ): List<Series> = seriesRemoteDataSource.getSeriesRecommendations(
        id = id,
        page = page
    ).results?.mapNotNull {
        runCatching { it.toDomain() }
            .getOrNull()
    } ?: emptyList()

    override suspend fun getSeriesByGenreId(genreId: Int, page: Int): List<Series> =
        seriesRemoteDataSource.getSeriesByGenreId(
            genreId = genreId,
            page = page
        ).results?.map { it.toDomain() } ?: emptyList()

    override suspend fun getSeriesReviews(id: Int, page: Int): List<Review> =
        seriesRemoteDataSource.getSeriesReviews(
            id,
            page
        ).results?.mapNotNull { runCatching { it.toDomain() }.getOrNull() }
            ?: emptyList()

    override suspend fun getSeriesCreditsDetails(id: Int): CreditsInfo =
        seriesRemoteDataSource.getSeriesCredits(seriesId = id).toDomain()

    override suspend fun getTopRatedTVSeries(
        page: Int,
        forceRefresh: Boolean
    ): List<Series> = homeCacheHelper.getCachedOrFetchHomeItems(
        categoryType = CATEGORY_TOP_RATED_TV,
        fetchFromRemote = {
            seriesRemoteDataSource.getTopRatedTVSeries(page).results?.map { it.toDomain() }
                ?: emptyList()
        },
        mapFromEntity = { it.toSeries() },
        forceRefresh = forceRefresh
    )

    private companion object {
        const val CATEGORY_TOP_RATED_TV = "TOP_RATED_TV"
    }
}