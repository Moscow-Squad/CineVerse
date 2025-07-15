package com.remote.source

import com.remote.dto.MovieDetailDto
import com.remote.dto.SeriesDetailDto
import com.remote.dto.review.movie_review.ReviewDto
import com.utils.MOVIE
import com.utils.REVIEWS
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

    suspend fun getReviews(id: Int, page: Int, isMovie: Boolean): ReviewDto =
        client.performCall<Unit, ReviewDto>(
            method = HttpMethod.Get,
            path = (if (isMovie) MOVIE else SERIES) + id + REVIEWS,
            requestBuilder = {
                url {
                    parameters.append("page", page.toString())
                }
            }
        )
}