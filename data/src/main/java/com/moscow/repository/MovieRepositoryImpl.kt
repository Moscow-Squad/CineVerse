package com.moscow.repository

import com.moscow.data_source.local.DetailsLocalDataSource
import com.moscow.data_source.remote.MovieRemoteDataSource
import com.moscow.domain.model.CreditsDetails
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Review
import com.moscow.domain.model.details.MovieDetail
import com.moscow.domain.repository.MovieRepository
import com.moscow.mapper.toDomain
import com.moscow.remote.dto.review.RatingRequestDto
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val detailLocalDataSource: DetailsLocalDataSource
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): List<Movie> {
        return movieRemoteDataSource.getPopularMovies(page = page).results?.map { it.toDomain() }
            ?: emptyList()
    }

    override suspend fun getMoviesDetail(id: Int): MovieDetail {
        val trailer = movieRemoteDataSource
            .getMovieTrailer(id)
            .trailers
            .firstOrNull{ it.key != null}
            ?.key ?: ""
        val res = movieRemoteDataSource.getMovieDetails(id)
        res.genres?.forEach { detailLocalDataSource.insertFavouriteGenre(it.id) }
        return res.toDomain(trailer)
    }

    override suspend fun getMovieCredits(id: Int): CreditsDetails {
        val response = movieRemoteDataSource.getMovieCredits(id)
        return response.toDomain()
    }

    override suspend fun rateMovie(
        id: Int,
        rating: Float
    ) {
        movieRemoteDataSource.rateMovie(
            id = id,
            rating = RatingRequestDto(value = rating)
        )
    }

    override suspend fun getMovieRecommendations(
        id: Int,
        page: Int
    ): List<Movie> {
        val movies = movieRemoteDataSource.getMoviesRecommendations(id, page)
        return movies.results?.mapNotNull { runCatching { it.toDomain() }.getOrNull() } ?: emptyList()

    }

    override suspend fun getMoviesByGenreId(genreId: Int, page: Int): List<Movie> {
        return movieRemoteDataSource.getMoviesByGenreId(
            genreId,
            page
        ).results?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getMovieReviews(id: Int, page: Int): List<Review> {
        val reviews = movieRemoteDataSource.getMovieReviews(id, page)
        return reviews.results?.mapNotNull { runCatching { it.toDomain() }.getOrNull() }
            ?: emptyList()
    }
}
