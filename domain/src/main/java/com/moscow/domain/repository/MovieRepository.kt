package com.moscow.domain.repository

import com.moscow.domain.model.CreditsDetails
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Review
import com.moscow.domain.model.details.MovieDetail

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): List<Movie>
    suspend fun getMoviesDetail(id: Int): MovieDetail
    suspend fun rateMovie(id: Int, rating: Float)
    suspend fun getMovieCredits(id: Int): CreditsDetails
    suspend fun getMovieRecommendations(id: Int, page: Int): List<Movie>
    suspend fun getMoviesByGenreId(genreId: Int, page: Int): List<Movie>
    suspend fun getMovieReviews(id: Int, page: Int): List<Review>
}