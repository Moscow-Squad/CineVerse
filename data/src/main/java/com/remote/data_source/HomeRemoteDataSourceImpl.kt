package com.remote.data_source

import com.data_source.remote.HomeRemoteDataSource
import com.remote.dto.MovieDto
import com.remote.dto.series.SeriesDto
import com.remote.services.HomeService
import com.utils.ApiResponse
import com.utils.handleApi

class HomeRemoteDataSourceImpl(
    private val homeService: HomeService
): HomeRemoteDataSource{
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
        homeService.getMatchYourVibeMovies(genreId,page)
    }

    override suspend fun getTopRatedTVSeries(page: Int): ApiResponse<SeriesDto> = handleApi { 
        homeService.getTopRatedTVSeries(page)
    }
}