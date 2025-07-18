package com.remote.source

import com.remote.dto.CreditsDetailsDto
import com.remote.dto.details.ListOfSeriesDto
import com.remote.dto.details.MovieDetailDto
import com.remote.dto.MovieDto
import com.remote.dto.details.SeriesDetailDto
import com.remote.dto.review.ReviewDto
import com.utils.ApiResponse
import com.utils.CREDITS
import com.utils.LATEST
import com.utils.LISTS
import com.utils.MOVIE
import com.utils.RECOMMENDATIONS
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

    suspend fun getCredits(movieID:Int): CreditsDetailsDto =
        client.performCall<Unit, CreditsDetailsDto>(
            method = HttpMethod.Get,
            path = MOVIE + movieID + CREDITS
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
    suspend fun getLatestSeasons(): List<SeriesDetailDto> =
        client.performCall<Unit, List<SeriesDetailDto>>(
            method = HttpMethod.Get,
            path = SERIES + LATEST
        )
    suspend fun getListOfSeries(id: Int, page: Int): ListOfSeriesDto =
        client.performCall<Unit, ListOfSeriesDto>(
            method = HttpMethod.Get,
            path = SERIES + id + LISTS,
            requestBuilder = {
                url {
                    parameters.append("page", page.toString())
                }
            }
        )

    suspend fun getRecommendations(movieID: Int,page:Int):List<MovieDto> =
        client.performCall<Unit, ApiResponse<MovieDto>>(
            method = HttpMethod.Get,
            path = MOVIE + movieID + RECOMMENDATIONS,
            requestBuilder = {
                url {
                    parameters.append("page", page.toString())
                }
            }
        ).results
}