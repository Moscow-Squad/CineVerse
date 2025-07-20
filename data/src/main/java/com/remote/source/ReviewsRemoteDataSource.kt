package com.remote.source

import com.remote.dto.review.ReviewDto
import com.services.ReviewsService
import com.utils.MOVIE
import com.utils.REVIEWS
import com.utils.SERIES
import com.utils.performCall
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

class ReviewsRemoteDataSource (
    private val reviewsService: ReviewsService
){
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