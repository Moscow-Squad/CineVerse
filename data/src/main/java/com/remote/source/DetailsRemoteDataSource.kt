package com.remote.source

import com.remote.dto.MovieDetailDto
import com.remote.dto.SeriesDetailDto
import com.remote.dto.review.RatingRequestDto
import com.remote.dto.review.ReviewDto
import com.utils.MOVIE
import com.utils.RATING
import com.utils.REVIEWS
import com.utils.SERIES
import com.utils.SESSION_ID
import com.utils.performCall
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
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

    suspend fun rateMovie(rating: RatingRequestDto, movieId: Int) =
        client.performCall<RatingRequestDto, Unit>(
            method = HttpMethod.Post,
            path = "$MOVIE${movieId}$RATING",
            requestBuilder = {
                parameter(SESSION_ID, "31044f799b3ccf5e970b994ca0022ef8865c1e35")

            },
            body = rating
        )

    suspend fun rateSeries(rating: RatingRequestDto, seriesId: Int) =
        client.performCall<RatingRequestDto, Unit>(
            method = HttpMethod.Post,
            path = "$SERIES${seriesId}$RATING",
            requestBuilder = {
                parameter(SESSION_ID, "31044f799b3ccf5e970b994ca0022ef8865c1e35")

            },
            body = rating
        )


}
