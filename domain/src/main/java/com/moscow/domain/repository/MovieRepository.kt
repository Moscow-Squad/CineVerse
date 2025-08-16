package com.moscow.domain.repository

import com.moscow.domain.model.CreditsDetails
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Review
import com.moscow.domain.model.details.MovieDetail
import com.moscow.domain.usecase.rating.GetRatedMoviesUseCase

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): List<Movie>
    suspend fun getMoviesDetail(id: Int): MovieDetail
    suspend fun rateMovie(id: Int, rating: Float)
    suspend fun deleteRatingMovie(movieId: Int)
    suspend fun getRatedMovies(userId: Int, page : Int): List<GetRatedMoviesUseCase.RatedMovieResult>
    suspend fun getUserRatingForMovie(movieId: Int): Int
    suspend fun getMovieCredits(id: Int): CreditsDetails
    suspend fun getMovieRecommendations(id: Int, page: Int): List<Movie>
    suspend fun getMoviesByGenreId(genreId: Int, page: Int): List<Movie>
    suspend fun getMovieReviews(id: Int, page: Int): List<Review>
    suspend fun getMatchedMovies(
        page: Int,
        genres: String?,
        runtimeGte: Int?,
        runtimeLte: Int?,
        releaseDateGte: String?,
        releaseDateLte: String?
    ): List<Movie>
}