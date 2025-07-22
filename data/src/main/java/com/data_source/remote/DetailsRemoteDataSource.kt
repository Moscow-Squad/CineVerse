package com.data_source.remote

import com.remote.dto.CreditsDetailsDto
import com.remote.dto.series.ListOfSeriesDto
import com.remote.dto.details.MovieDetailDto
import com.remote.dto.series.SeriesDetailDto
import com.remote.dto.review.RatingRequestDto

interface DetailsRemoteDataSource {
    suspend fun getMovieDetails(id: Int): MovieDetailDto
    suspend fun getSeriesDetails(id: Int): SeriesDetailDto
    suspend fun getCredits(id: Int): CreditsDetailsDto
    suspend fun rateMovie(rating: RatingRequestDto, movieId: Int)
    suspend fun rateSeries(rating: RatingRequestDto, seriesId: Int)
    suspend fun getLatestSeasons(): SeriesDetailDto
    suspend fun getListOfSeries(id: Int, page: Int): ListOfSeriesDto
}