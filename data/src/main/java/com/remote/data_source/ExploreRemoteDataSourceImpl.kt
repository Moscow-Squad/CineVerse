package com.remote.data_source

import com.data_source.remote.ExploreRemoteDataSource
import com.remote.dto.GenreResponse
import com.remote.dto.MovieDto
import com.remote.dto.series.SeriesDto
import com.remote.services.ExploreService
import com.utils.ApiResponse
import com.utils.handleApi


class ExploreRemoteDataSourceImpl(
    private val exploreService: ExploreService
): ExploreRemoteDataSource {
    override suspend fun getMoviesGenres(): GenreResponse = handleApi {
        exploreService.getMoviesGenres()
    }


    override suspend fun getSeriesGenres(): GenreResponse = handleApi {
        exploreService.getSeriesGenres()
    }


    override suspend fun getMovies(page:Int): ApiResponse<MovieDto> = handleApi {
        exploreService.getMovies(page)
    }

    override suspend fun getSeries(page:Int): ApiResponse<SeriesDto> = handleApi {
        exploreService.getSeries(page)
    }

    override suspend fun getSeriesByGenreId(genreId: Int, page:Int): ApiResponse<SeriesDto> = handleApi {
        exploreService.getSeriesByGenreId(genreId,page)
    }


    override suspend fun getMoviesByGenreId(genreId: Int, page: Int): ApiResponse<MovieDto> = handleApi {
        exploreService.getMoviesByGenreId(genreId,page)
    }

}