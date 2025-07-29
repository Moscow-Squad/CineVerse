package com.moscow.repository

import com.moscow.data_source.local.DetailsLocalDataSource
import com.moscow.data_source.remote.SeriesRemoteDataSource
import com.moscow.domain.model.CreditsDetails
import com.moscow.domain.model.Review
import com.moscow.domain.model.Series
import com.moscow.domain.model.details.ListOfSeries
import com.moscow.domain.model.details.Season
import com.moscow.domain.model.details.SeriesDetail
import com.moscow.domain.repository.SeriesRepository
import com.moscow.mapper.toDomain
import com.moscow.remote.dto.review.RatingRequestDto
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val seriesRemoteDataSource: SeriesRemoteDataSource,
    private val detailsLocalDataSource: DetailsLocalDataSource
): SeriesRepository {
    override suspend fun getPopularSeries(page: Int): List<Series> {
        return seriesRemoteDataSource.getPopularSeries(page).results?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getSeriesDetail(id: Int): SeriesDetail {
        val res = seriesRemoteDataSource.getSeriesDetails(id)
        res.genres.forEach { detailsLocalDataSource.insertFavouriteGenre(it.id) }
        return res.toDomain()
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

    override suspend fun getLatestSeasons(): List<Season> {
        val response = seriesRemoteDataSource.getLatestSeasons()
        return response.seasons.map { it.toDomain() }
    }


    override suspend fun getListOfSeries(id: Int, page: Int): List<ListOfSeries> {
        val response = seriesRemoteDataSource.getListOfSeries(id, page)
        return listOf(response.toDomain())
    }

    override suspend fun getSeriesRecommendations(
        id: Int,
        page: Int
    ): List<Series> {
        val series = seriesRemoteDataSource.getSeriesRecommendations(id, page)
        return series.results?.mapNotNull { runCatching { it.toDomain() }.getOrNull() } ?: emptyList()
    }

    override suspend fun getSeriesByGenreId(genreId: Int, page: Int): List<Series> {
        return seriesRemoteDataSource.getSeriesByGenreId(
            genreId,
            page
        ).results?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getSeriesReviews(id: Int, page: Int): List<Review> {
        val reviews = seriesRemoteDataSource.getSeriesReviews(id, page)
        return reviews.results?.mapNotNull { runCatching { it.toDomain() }.getOrNull() }
            ?: emptyList()
    }

    override suspend fun getSeriesCreditsDetails(id: Int): CreditsDetails {
        val response = seriesRemoteDataSource.getSeriesCredits(id)
        return response.toDomain()
    }
}
