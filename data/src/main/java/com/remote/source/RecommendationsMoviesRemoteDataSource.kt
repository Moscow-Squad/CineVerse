package com.remote.source

import com.remote.dto.MovieDto
import com.remote.dto.details.SeriesRecommendationDto
import com.remote.dto.review.ReviewDto
import com.utils.ApiResponse
import com.utils.MOVIE
import com.utils.RECOMMENDATIONS
import com.utils.REVIEWS
import com.utils.SERIES
import com.utils.performCall
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

class RecommendationsMoviesRemoteDataSource (
    private val client: HttpClient,
){
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

    suspend fun getSeriesRecommendations(seriesId: Int,page:Int):List<SeriesRecommendationDto> =
        client.performCall<Unit, ApiResponse<SeriesRecommendationDto>>(
            method = HttpMethod.Get,
            path = SERIES + seriesId + RECOMMENDATIONS,
            requestBuilder = {
                url {
                    parameters.append("page", page.toString())
                }
            }
        ).results
}