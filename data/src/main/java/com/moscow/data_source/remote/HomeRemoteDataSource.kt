package com.data_source.remote

import com.moscow.remote.dto.MovieDto
import com.moscow.remote.dto.series.SeriesDto
import com.moscow.utils.ApiResponse


interface HomeRemoteDataSource {

    suspend fun getTrendingMovies(time: String?): ApiResponse<MovieDto>

    suspend fun getUpComingMovies(page: Int): ApiResponse<MovieDto>

    suspend fun getRecentlyReleasedMovies(page: Int): ApiResponse<MovieDto>

    suspend fun getMatchYourVibeMovies(genreId: Int, page: Int): ApiResponse<MovieDto>

    suspend fun getTopRatedTVSeries(page: Int): ApiResponse<SeriesDto>
}