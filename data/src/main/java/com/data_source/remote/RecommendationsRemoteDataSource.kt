package com.data_source.remote

import com.utils.ApiResponse
import com.remote.dto.MovieDto
import com.remote.dto.series.SeriesDto

interface RecommendationsRemoteDataSource {
    suspend fun getMoviesRecommendations(movieID: Int, page: Int): ApiResponse<MovieDto>
    suspend fun getSeriesRecommendations(seriesID: Int, page: Int): ApiResponse<SeriesDto>
}