package com.android.domain.repository

import com.android.domain.model.CreditsDetails
import com.android.domain.model.Movie
import com.android.domain.model.Review
import com.android.domain.model.details.MovieDetail

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): List<Movie>
    suspend fun getMoviesDetail(id: Int): MovieDetail
    suspend fun rateMovie(id: Int, rating: Float)
    suspend fun getMovieCredits(id: Int): CreditsDetails
    suspend fun getMovieRecommendations(id: Int, page: Int): List<Movie>
    suspend fun getMoviesByGenreId(genreId: Int, page: Int): List<Movie>
    suspend fun getMovieReviews(id: Int, page: Int): List<Review>
}