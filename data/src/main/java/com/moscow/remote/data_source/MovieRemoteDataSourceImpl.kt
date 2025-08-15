package com.moscow.remote.data_source

import com.moscow.data_source.remote.MovieRemoteDataSource
import com.moscow.domain.repository.PreferenceRepository
import com.moscow.remote.dto.CreditsDetailsDto
import com.moscow.remote.dto.MovieDto
import com.moscow.remote.dto.details.MediaTrailersDto
import com.moscow.remote.dto.details.MovieDetailDto
import com.moscow.remote.dto.rating.UserRatingResponse
import com.moscow.remote.dto.rating.movie.RatedMovieDto
import com.moscow.remote.dto.review.RatingRequestDto
import com.moscow.remote.dto.review.ReviewDto
import com.moscow.remote.services.MovieService
import com.moscow.utils.ApiResponse
import com.moscow.utils.handleApi
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val movieService: MovieService,
    private val preferenceRepository: PreferenceRepository
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
        val sessionId = preferenceRepository.getSessionId()
        handleApi {
            movieService.rateMovie(
                id,
                sessionId,
                rating
            )
        }
    }

    override suspend fun deleteRatingMovie(movieId: Int){
        val sessionId = preferenceRepository.getSessionId()
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
        val sessionId = preferenceRepository.getSessionId()
        return handleApi {
            movieService.getRatedMovies(
                userId,
                sessionId,
                page
            )
        }
    }

    override suspend fun getUserRatingForMovie(movieId: Int): UserRatingResponse {
        val sessionId = preferenceRepository.getSessionId()
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

    override suspend fun getMatchedMovies(
        page: Int,
        genres: String?,
        runtimeGte: Int?,
        runtimeLte: Int?,
        releaseDateGte: String?,
        releaseDateLte: String?
    ): ApiResponse<MovieDto> {
        return handleApi {
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
}