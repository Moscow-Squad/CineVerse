package com.remote.data_source

import com.data_source.remote.HomeRemoteDataSource
import com.moscow.remote.dto.MovieDto
import com.moscow.remote.dto.series.SeriesDto
import com.moscow.utils.ApiResponse
import com.moscow.utils.DAY
import com.moscow.utils.handleApi
import com.remote.services.HomeService

class HomeRemoteDataSourceImpl(
    private val homeService: HomeService
) : HomeRemoteDataSource {
    override suspend fun getTrendingMovies(time: String?): ApiResponse<MovieDto> = handleApi {
        homeService.getTrendingMovies(time ?: DAY)
    }

    override suspend fun getUpComingMovies(page: Int): ApiResponse<MovieDto> = handleApi {
        homeService.getUpComingMovies(page)
    }

    override suspend fun getRecentlyReleasedMovies(page: Int): ApiResponse<MovieDto> = handleApi {
        homeService.getRecentlyReleasedMovies(page)
    }

    override suspend fun getMatchYourVibeMovies(
        genreId: Int,
        page: Int
    ): ApiResponse<MovieDto> = handleApi {
        homeService.getMatchYourVibeMovies(genreId, page)
    }

    override suspend fun getTopRatedTVSeries(page: Int): ApiResponse<SeriesDto> = handleApi {
        homeService.getTopRatedTVSeries(page)
    }
}