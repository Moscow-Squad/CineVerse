package com.moscow.domain.repository

import com.moscow.domain.model.CreditsInfo
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Review
import com.moscow.domain.usecase.rating.GetRatedMoviesUseCase

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): List<Movie>
    suspend fun getDetailsMovie(id: Int): Movie
    suspend fun addRatingMovie(id: Int, rating: Float)
    suspend fun deleteRatingMovie(movieId: Int)
    suspend fun getRatedMovies(userId: Int, page: Int): List<GetRatedMoviesUseCase.RatedMovieResult>
    suspend fun getUserRatingMovie(movieId: Int): Int
    suspend fun getCreditsMovie(id: Int): CreditsInfo
    suspend fun getRecommendationsMovie(id: Int, page: Int): List<Movie>
    suspend fun getMoviesByGenreId(genreId: Int, page: Int): List<Movie>
    suspend fun getTrendingMovies(forceRefresh: Boolean = false): List<Movie>
    suspend fun getUpComingMovies(page: Int, forceRefresh: Boolean = false): List<Movie>
    suspend fun getRecentlyReleasedMovies(page: Int, forceRefresh: Boolean = false): List<Movie>
    suspend fun getMatchYourVibeMovies(
        genreId: Int,
        page: Int,
        forceRefresh: Boolean = false
    ): List<Movie>

    suspend fun getReviewsMovie(id: Int, page: Int): List<Review>
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