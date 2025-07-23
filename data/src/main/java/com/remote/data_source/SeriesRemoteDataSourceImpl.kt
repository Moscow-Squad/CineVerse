package com.remote.data_source

import com.data_source.remote.SeriesRemoteDataSource
import com.remote.dto.review.RatingRequestDto
import com.remote.dto.review.ReviewDto
import com.remote.dto.series.ListOfSeriesDto
import com.remote.dto.series.SeriesDetailDto
import com.remote.dto.series.SeriesDto
import com.remote.services.SeriesService
import com.utils.ApiResponse
import com.utils.handleApi

class SeriesRemoteDataSourceImpl(
    private val seriesService: SeriesService
) : SeriesRemoteDataSource {

    override suspend fun getPopularSeries(page: Int): ApiResponse<SeriesDto> =
        handleApi {
            seriesService.getPopularSeries(page)
        }

    override suspend fun getSeriesDetails(id: Int): SeriesDetailDto =
        handleApi {
            seriesService.getSeriesDetails(id)
        }

    override suspend fun rateSeries(rating: RatingRequestDto, id: Int) =
        handleApi {
            seriesService.rateSeries(
                id,
                "31044f799b3ccf5e970b994ca0022ef8865c1e35",
                rating
            )
        }

    override suspend fun getLatestSeasons(): SeriesDetailDto =
        handleApi {
            seriesService.getLatestSeasons()
        }

    override suspend fun getListOfSeries(id: Int, page: Int): ListOfSeriesDto =
        handleApi {
            seriesService.getListOfSeries(
                id,
                page
            )
        }

    override suspend fun getSeriesReviews(id: Int, page: Int): ApiResponse<ReviewDto> =
        handleApi {
            seriesService.getSeriesReviews(id, page)
        }

    override suspend fun getSeriesRecommendations(
        id: Int,
        page: Int
    ): ApiResponse<SeriesDto> =
        handleApi {
            seriesService.getSeriesRecommendations(id, page)
        }

    override suspend fun getSeriesByGenreId(genreId: Int, page: Int): ApiResponse<SeriesDto> =
        handleApi {
            seriesService.getSeriesByGenreId(genreId, page)
        }
}