package com.moscow.remote.data_source

import com.moscow.data_source.remote.MovieRemoteDataSource
import com.moscow.remote.dto.CreditsDetailsDto
import com.moscow.remote.dto.MovieDto
import com.moscow.remote.dto.details.MovieDetailDto
import com.moscow.remote.dto.review.RatingRequestDto
import com.moscow.remote.dto.review.ReviewDto
import com.moscow.remote.services.MovieService
import com.moscow.utils.ApiResponse
import com.moscow.utils.handleApi
import javax.inject.Inject

class MovieRemoteDataSourceImpl  @Inject constructor(
    private val movieService: MovieService
) : MovieRemoteDataSource {

    override suspend fun getPopularMovies(page: Int): ApiResponse<MovieDto> =
        handleApi {
            movieService.getPopularMovies(page)
        }

    override suspend fun getMovieDetails(id: Int): MovieDetailDto =
        handleApi {
            movieService.getMovieDetails(id)
        }

    override suspend fun rateMovie(rating: RatingRequestDto, id: Int) =
        handleApi {
            movieService.rateMovie(
                id,
                "31044f799b3ccf5e970b994ca0022ef8865c1e35",
                rating
            )
        }


    override suspend fun getMovieCredits(id: Int): CreditsDetailsDto =
        handleApi {
            movieService.getMovieCredits(id)
        }


    override suspend fun getMovieReviews(id: Int, page: Int): ApiResponse<ReviewDto> =
        handleApi {
            movieService.getMovieReviews(id, page)
        }


    override suspend fun getMoviesRecommendations(id: Int, page: Int): ApiResponse<MovieDto> =
        handleApi {
            movieService.getMoviesRecommendations(id, page)
        }


    override suspend fun getMoviesByGenreId(genreId: Int, page: Int): ApiResponse<MovieDto> =
        handleApi {
            movieService.getMoviesByGenreId(genreId, page)
        }
}