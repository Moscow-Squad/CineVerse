package com.data_source.remote

import com.remote.dto.review.RatingRequestDto
import com.remote.dto.review.ReviewDto
import com.remote.dto.series.ListOfSeriesDto
import com.remote.dto.series.SeriesDetailDto
import com.remote.dto.series.SeriesDto
import com.utils.ApiResponse

interface SeriesRemoteDataSource {
    suspend fun getPopularSeries(page:Int): ApiResponse<SeriesDto>
    suspend fun getSeriesDetails(id: Int): SeriesDetailDto
    suspend fun rateSeries(rating: RatingRequestDto, id: Int): ApiResponse<Nothing>
    suspend fun getSeriesReviews(id: Int, page: Int): ApiResponse<ReviewDto>
    suspend fun getListOfSeries(id: Int, page: Int): ListOfSeriesDto
    suspend fun getLatestSeasons(): SeriesDetailDto
    suspend fun getSeriesRecommendations(id: Int, page: Int): ApiResponse<SeriesDto>
    suspend fun getSeriesByGenreId(genreId: Int, page: Int): ApiResponse<SeriesDto>
}