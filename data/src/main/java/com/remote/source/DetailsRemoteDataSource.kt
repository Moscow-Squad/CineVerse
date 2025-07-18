package com.remote.source

import com.remote.dto.CreditsDetailsDto
import com.remote.dto.MovieDetailDto
import com.remote.dto.MovieDto
import com.remote.dto.SeriesDetailDto
import com.utils.ApiResponse
import com.utils.CREDITS
import com.utils.MOVIE
import com.utils.RECOMMENDATIONS
import com.utils.SERIES
import com.utils.performCall
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

class DetailsRemoteDataSource(
    private val client: HttpClient,
) {
    suspend fun getMovieDetails(id: Int): MovieDetailDto =
        client.performCall<Unit, MovieDetailDto>(
            method = HttpMethod.Get,
            path = MOVIE + id
        )

    suspend fun getSeriesDetails(id: Int): SeriesDetailDto =
        client.performCall<Unit, SeriesDetailDto>(
            method = HttpMethod.Get,
            path = SERIES + id
        )

    suspend fun getCredits(movieID:Int): CreditsDetailsDto =
        client.performCall<Unit, CreditsDetailsDto>(
            method = HttpMethod.Get,
            path = MOVIE + movieID + CREDITS
        )




}