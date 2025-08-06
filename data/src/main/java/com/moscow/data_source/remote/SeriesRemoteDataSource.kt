package com.moscow.data_source.remote

import com.moscow.remote.dto.details.MediaTrailersDto
import com.moscow.remote.dto.details.SeriesCreditDto
import com.moscow.remote.dto.rating.UserRatingResponse
import com.moscow.remote.dto.rating.series.RatedSeriesDto
import com.moscow.remote.dto.review.RatingRequestDto
import com.moscow.remote.dto.review.ReviewDto
import com.moscow.remote.dto.series.ListOfSeriesDto
import com.moscow.remote.dto.series.SeriesDetailDto
import com.moscow.remote.dto.series.SeriesDto
import com.moscow.utils.ApiResponse

interface SeriesRemoteDataSource {
    suspend fun getPopularSeries(page: Int): ApiResponse<SeriesDto>
    suspend fun getSeriesDetails(id: Int): SeriesDetailDto
    suspend fun rateSeries(rating: RatingRequestDto, id: Int)
    suspend fun deleteRatingSeries(seriesId: Int)
    suspend fun getRatedSeries(userId: Int, page: Int): ApiResponse<RatedSeriesDto>
    suspend fun getUserRatingForSeries(seriesId: Int) : UserRatingResponse
    suspend fun getSeriesReviews(id: Int, page: Int): ApiResponse<ReviewDto>
    suspend fun getListOfSeries(id: Int, page: Int): ListOfSeriesDto
    suspend fun getLatestSeasons(): SeriesDetailDto
    suspend fun getSeriesRecommendations(id: Int, page: Int): ApiResponse<SeriesDto>
    suspend fun getSeriesByGenreId(genreId: Int, page: Int): ApiResponse<SeriesDto>
    suspend fun getSeriesCredits(seriesId: Int): SeriesCreditDto
    suspend fun getSeriesTrailers(seriesId: Int): MediaTrailersDto
}