package com.moscow.data_source.remote

import com.moscow.remote.dto.media_item.common.CreditsDetailsDto
import com.moscow.remote.dto.media_item.movie.MovieDto
import com.moscow.remote.dto.media_item.common.MediaTrailersDto
import com.moscow.remote.dto.media_item.movie.MovieDetailDto
import com.moscow.remote.dto.rating.response.UserRatingResponse
import com.moscow.remote.dto.rating.response.RatedMovieDto
import com.moscow.remote.dto.rating.request.RatingRequestDto
import com.moscow.remote.dto.review.ReviewDto
import com.moscow.utils.ApiResponse

interface MovieRemoteDataSource {
    suspend fun getPopularMovies(page: Int): ApiResponse<MovieDto>
    suspend fun getMovieDetails(id: Int): MovieDetailDto
    suspend fun rateMovie(rating: RatingRequestDto, id: Int)
    suspend fun deleteRatingMovie(movieId: Int)
    suspend fun getRatedMovies(userId: Int, page: Int): ApiResponse<RatedMovieDto>
    suspend fun getUserRatingForMovie(movieId: Int) : UserRatingResponse
    suspend fun getMovieReviews(id: Int, page: Int): ApiResponse<ReviewDto>
    suspend fun getMovieCredits(id: Int): CreditsDetailsDto
    suspend fun getMoviesRecommendations(id: Int, page: Int): ApiResponse<MovieDto>
    suspend fun getMoviesByGenreId(genreId: Int, page: Int): ApiResponse<MovieDto>
    suspend fun getMovieTrailer(id: Int): MediaTrailersDto
    suspend fun getTrendingMovies(): ApiResponse<MovieDto>
    suspend fun getUpComingMovies(page: Int): ApiResponse<MovieDto>
    suspend fun getRecentlyReleasedMovies(page: Int): ApiResponse<MovieDto>
    suspend fun getMatchYourVibeMovies(genreId: Int, page: Int): ApiResponse<MovieDto>

}