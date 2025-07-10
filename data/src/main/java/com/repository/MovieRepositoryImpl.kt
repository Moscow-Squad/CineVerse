package com.repository

import com.android.domain.MovieRepository
import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.remote.ExploreApiService
import com.remote.mapper.MovieMapper
import com.utils.BaseRepository

class MovieRepositoryImpl(
    private val exploreApiService: ExploreApiService,
    private val mapper: MovieMapper
) :MovieRepository, BaseRepository() {
    override suspend fun geMovies(): List<Movie> = exploreApiService.getMovies().results.map { dto ->
        val details = exploreApiService.getMovieDetails(dto.id)
        mapper.mapToMovie(dto,details)
    }

    override suspend fun getSeries(): List<Series> = exploreApiService.getSeries().results.map { dto ->
        val details = exploreApiService.getSeriesDetails(dto.id)
        mapper.mapToSeries(dto, details)
    }
}