package com.remote.source

import com.remote.dto.GenreResponse
import com.remote.dto.MovieDto
import com.remote.dto.details.SeriesDto
import com.remote.services.ExploreService
import com.utils.ApiResponse
import com.utils.handleApi


class ExploreRemoteDataSource(
    private val exploreService: ExploreService
) {
    suspend fun getMoviesGenres(): GenreResponse = handleApi {
        exploreService.getMoviesGenres()
    }


    suspend fun getSeriesGenres(): GenreResponse = handleApi {
        exploreService.getSeriesGenres()
    }


    suspend fun getMovies(page:Int): ApiResponse<MovieDto> = handleApi {
        exploreService.getMovies(page)
    }

    suspend fun getSeries(page:Int): ApiResponse<SeriesDto> = handleApi {
        exploreService.getSeries(page)
    }

    suspend fun getSeriesByGenreId(genreId: Int): ApiResponse<SeriesDto> = handleApi {
        exploreService.getSeriesByGenreId(genreId)
    }


    suspend fun getMoviesByGenreId(genreId: Int): ApiResponse<MovieDto> = handleApi {
        exploreService.getMoviesByGenreId(genreId)
    }

}