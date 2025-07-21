package com.remote.source

import com.remote.dto.MovieDto
import com.remote.dto.details.SeriesDto
import com.remote.services.RecommendationsService
import com.utils.ApiResponse
import com.utils.handleApi


class RecommendationsRemoteDataSource (
    private val recommendationsService: RecommendationsService,
){
    suspend fun getMoviesRecommendations(movieID: Int,page:Int): ApiResponse<MovieDto> = handleApi {
        recommendationsService.getMoviesRecommendations(movieID , page)
    }
    suspend fun getSeriesRecommendations(seriesID: Int,page:Int): ApiResponse<SeriesDto> = handleApi {
        recommendationsService.getSeriesRecommendations(seriesID , page)
    }
}