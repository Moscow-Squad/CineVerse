package com.remote

import com.remote.dto.MovieDetailsDto
import com.remote.dto.MovieDto
import com.remote.dto.PopularResponse
import com.remote.dto.SeriesDetailsDto
import com.remote.dto.SeriesDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ExploreApiService(
    private val client: HttpClient
) {
    suspend fun getMovies(): PopularResponse<MovieDto> =
        client.get("https://api.themoviedb.org/3/movie/popular") {
            parameter("language", "en-US")
            parameter("page", 1)
        }.body()
    suspend fun getMovieDetails(id: Int): MovieDetailsDto =
        client.get("https://api.themoviedb.org/3/movie/$id") {
            parameter("language", "en-US")
        }.body()

    suspend fun getSeries(): PopularResponse<SeriesDto> =
        client.get("https://api.themoviedb.org/3/tv/popular") {
            parameter("language", "en-US")
            parameter("page", 1)
        }.body()
    suspend fun getSeriesDetails(id: Int): SeriesDetailsDto =
        client.get("https://api.themoviedb.org/3/tv/$id") {
            parameter("language", "en-US")
        }.body()
}
