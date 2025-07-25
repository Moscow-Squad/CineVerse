package com.moscow.data_source.remote

import com.moscow.remote.dto.CreditsDetailsDto
import com.moscow.remote.dto.MovieDto
import com.moscow.remote.dto.details.MovieDetailDto
import com.moscow.remote.dto.review.RatingRequestDto
import com.moscow.remote.dto.review.ReviewDto
import com.moscow.utils.ApiResponse

interface MovieRemoteDataSource {
    suspend fun getPopularMovies(page:Int): ApiResponse<MovieDto>
    suspend fun getMovieDetails(id: Int): MovieDetailDto
    suspend fun rateMovie(rating: RatingRequestDto, id: Int): ApiResponse<Nothing>
    suspend fun getMovieReviews(id: Int, page: Int): ApiResponse<ReviewDto>
    suspend fun getMovieCredits(id: Int): CreditsDetailsDto
    suspend fun getMoviesRecommendations(id: Int, page: Int): ApiResponse<MovieDto>
    suspend fun getMoviesByGenreId(genreId: Int, page: Int): ApiResponse<MovieDto>
}