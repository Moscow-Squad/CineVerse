package com.moscow.remote.data_source

import com.moscow.data_source.remote.MovieRemoteDataSource
import com.moscow.domain.repository.auth.UserRepository
import com.moscow.remote.dto.media_item.common.CreditsDetailsDto
import com.moscow.remote.dto.media_item.movie.MovieDto
import com.moscow.remote.dto.media_item.common.MediaTrailersDto
import com.moscow.remote.dto.media_item.movie.MovieDetailDto
import com.moscow.remote.dto.rating.response.UserRatingResponse
import com.moscow.remote.dto.rating.response.RatedMovieDto
import com.moscow.remote.dto.rating.request.RatingRequestDto
import com.moscow.remote.dto.review.ReviewDto
import com.moscow.remote.services.MovieService
import com.moscow.utils.ApiResponse
import com.moscow.utils.handleApi
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val movieService: MovieService,
    private val userRepository: UserRepository
) : MovieRemoteDataSource {

    override suspend fun getPopularMovies(page: Int): ApiResponse<MovieDto> =
        handleApi {
            movieService.getPopularMovies(page)
        }

    override suspend fun getMovieDetails(id: Int): MovieDetailDto =
        handleApi {
            movieService.getMovieDetails(id)
        }

    override suspend fun rateMovie(rating: RatingRequestDto, id: Int) {
        val sessionId = userRepository.getSessionId()
        handleApi {
            movieService.rateMovie(
                id,
                sessionId,
                rating
            )
        }
    }

    override suspend fun deleteRatingMovie(movieId: Int) {
        val sessionId = userRepository.getSessionId()
        handleApi {
            movieService.deleteRatingMovie(
                movieId,
                sessionId
            )
        }
    }

    override suspend fun getRatedMovies(
        userId: Int,
        page: Int
    ): ApiResponse<RatedMovieDto> {
        val sessionId = userRepository.getSessionId()
        return handleApi {
            movieService.getRatedMovies(
                userId,
                sessionId,
                page
            )
        }
    }

    override suspend fun getUserRatingForMovie(movieId: Int): UserRatingResponse {
        val sessionId = userRepository.getSessionId()
        return handleApi {
            movieService.getUserRatingForMovie(
                movieId,
                sessionId
            )
        }
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

    override suspend fun getMovieTrailer(id: Int): MediaTrailersDto =
        handleApi {
            movieService.getMovieTrailers(id)
        }

    override suspend fun getTrendingMovies(): ApiResponse<MovieDto> = handleApi {
        movieService.getTrendingMovies()
    }

    override suspend fun getUpComingMovies(page: Int): ApiResponse<MovieDto> = handleApi {
        movieService.getUpComingMovies(page)
    }

    override suspend fun getRecentlyReleasedMovies(page: Int): ApiResponse<MovieDto> = handleApi {
        movieService.getRecentlyReleasedMovies(page)
    }

    override suspend fun getMatchYourVibeMovies(
        genreId: Int,
        page: Int
    ): ApiResponse<MovieDto> = handleApi {
        movieService.getMatchYourVibeMovies(genreId, page)
    }

    override suspend fun getMatchedMovies(
        page: Int,
        genres: String?,
        runtimeGte: Int?,
        runtimeLte: Int?,
        releaseDateGte: String?,
        releaseDateLte: String?
    ): ApiResponse<MovieDto> =
        handleApi {
            movieService.getMatchedMovies(
                page,
                genres,
                runtimeGte,
                runtimeLte,
                releaseDateGte,
                releaseDateLte
            )
        }
}