package com.data_source.remote

import com.remote.dto.CreditsDetailsDto
import com.remote.dto.MovieDto
import com.remote.dto.details.MovieDetailDto
import com.remote.dto.review.RatingRequestDto
import com.remote.dto.review.ReviewDto
import com.utils.ApiResponse

interface MovieRemoteDataSource {
    suspend fun getPopularMovies(page:Int): ApiResponse<MovieDto>
    suspend fun getMovieDetails(id: Int): MovieDetailDto
    suspend fun rateMovie(rating: RatingRequestDto, id: Int): ApiResponse<Nothing>
    suspend fun getMovieReviews(id: Int, page: Int): ApiResponse<ReviewDto>
    suspend fun getMovieCredits(id: Int): CreditsDetailsDto
    suspend fun getMoviesRecommendations(id: Int, page: Int): ApiResponse<MovieDto>
    suspend fun getMoviesByGenreId(genreId: Int, page: Int): ApiResponse<MovieDto>
}