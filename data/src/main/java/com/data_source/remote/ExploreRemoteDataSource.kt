package com.data_source.remote

import com.remote.dto.GenreResponse
import com.utils.ApiResponse
import com.remote.dto.MovieDto
import com.remote.dto.series.SeriesDto

interface ExploreRemoteDataSource {
    suspend fun getMoviesGenres(): GenreResponse
    suspend fun getSeriesGenres(): GenreResponse
    suspend fun getMovies(page: Int): ApiResponse<MovieDto>
    suspend fun getSeries(page: Int): ApiResponse<SeriesDto>
    suspend fun getSeriesByGenreId(genreId: Int, page: Int): ApiResponse<SeriesDto>
    suspend fun getMoviesByGenreId(genreId: Int, page: Int): ApiResponse<MovieDto>
}