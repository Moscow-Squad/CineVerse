package com.remote.data_source

import com.data_source.remote.RecommendationsRemoteDataSource
import com.remote.dto.MovieDto
import com.remote.dto.series.SeriesDto
import com.remote.services.RecommendationsService
import com.utils.ApiResponse
import com.utils.handleApi


class RecommendationsRemoteDataSourceImpl (
    private val recommendationsService: RecommendationsService,
): RecommendationsRemoteDataSource{
    override suspend fun getMoviesRecommendations(movieID: Int, page:Int): ApiResponse<MovieDto> = handleApi {
        recommendationsService.getMoviesRecommendations(movieID , page)
    }
    override suspend fun getSeriesRecommendations(seriesID: Int, page:Int): ApiResponse<SeriesDto> = handleApi {
        recommendationsService.getSeriesRecommendations(seriesID , page)
    }
}