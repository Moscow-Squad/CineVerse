package com.moscow.repository

import com.moscow.data_source.local.DetailsLocalDataSource
import com.moscow.data_source.remote.MovieRemoteDataSource
import com.moscow.domain.model.CreditsInfo
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Review
import com.moscow.domain.repository.MovieRepository
import com.moscow.domain.usecase.rating.GetRatedMoviesUseCase.RatedMovieResult
import com.moscow.mapper.toDomain
import com.moscow.mapper.toMovie
import com.moscow.mapper.toOutputResult
import com.moscow.remote.dto.rating.request.RatingRequestDto
import com.moscow.utils.HomeCacheHelper
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val homeCacheHelper: HomeCacheHelper,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val detailLocalDataSource: DetailsLocalDataSource
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): List<Movie> {
        return movieRemoteDataSource.getPopularMovies(page = page).results?.map { it.toDomain() }
            ?: emptyList()
    }

    override suspend fun getDetailsMovie(id: Int): Movie {
        val trailer = movieRemoteDataSource
            .getMovieTrailer(id = id)
            .trailers
            .firstOrNull { it.key != null }
            ?.key ?: ""

        val movieDetails = movieRemoteDataSource.getMovieDetails(id)
        movieDetails.genres?.forEach { detailLocalDataSource.insertFavouriteGenre(it.id) }
        return movieDetails.toDomain(trailer)
    }

    override suspend fun getCreditsMovie(id: Int): CreditsInfo {
        val response = movieRemoteDataSource.getMovieCredits(id)
        return response.toDomain()
    }

    override suspend fun addRatingMovie(
        id: Int,
        rating: Float
    ) {
        movieRemoteDataSource.rateMovie(
            id = id,
            rating = RatingRequestDto(value = rating)
        )
    }

    override suspend fun deleteRatingMovie(movieId: Int) {
        movieRemoteDataSource.deleteRatingMovie(movieId)
    }

    override suspend fun getRatedMovies(
        userId: Int,
        page: Int
    ): List<RatedMovieResult> {
        val response = movieRemoteDataSource.getRatedMovies(userId, page)
        return response.results?.mapNotNull { it.toOutputResult() } ?: emptyList()
    }

    override suspend fun getUserRatingMovie(movieId: Int): Int {
        val response = movieRemoteDataSource.getUserRatingForMovie(movieId)
        return response.userRating ?: 0
    }

    override suspend fun getRecommendationsMovie(
        id: Int,
        page: Int
    ): List<Movie> {
        val movies = movieRemoteDataSource.getMoviesRecommendations(id, page)
        return movies.results?.mapNotNull { runCatching { it.toDomain() }.getOrNull() }
            ?: emptyList()
    }

    override suspend fun getMoviesByGenreId(genreId: Int, page: Int): List<Movie> {
        return movieRemoteDataSource.getMoviesByGenreId(
            genreId,
            page
        ).results?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getReviewsMovie(id: Int, page: Int): List<Review> {
        val reviews = movieRemoteDataSource.getMovieReviews(id, page)
        return reviews.results?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getTrendingMovies(forceRefresh: Boolean): List<Movie> {
        return homeCacheHelper.getCachedOrFetchHomeItems(
            categoryType = CATEGORY_TRENDING,
            fetchFromRemote = {
                movieRemoteDataSource.getTrendingMovies().results?.map { it.toDomain() }
                    ?: emptyList()
            },
            mapFromEntity = { it.toMovie() },
            forceRefresh = forceRefresh
        )
    }

    override suspend fun getRecentlyReleasedMovies(page: Int, forceRefresh: Boolean): List<Movie> {
        return homeCacheHelper.getCachedOrFetchHomeItems(
            categoryType = CATEGORY_RECENTLY_RELEASED,
            fetchFromRemote = {
                movieRemoteDataSource.getRecentlyReleasedMovies(page).results?.map { it.toDomain() }
                    ?: emptyList()
            },
            mapFromEntity = { it.toMovie() },
            forceRefresh = forceRefresh
        )
    }

    override suspend fun getUpComingMovies(page: Int, forceRefresh: Boolean): List<Movie> {
        return homeCacheHelper.getCachedOrFetchHomeItems(
            categoryType = CATEGORY_UPCOMING,
            fetchFromRemote = {
                movieRemoteDataSource.getUpComingMovies(page).results?.map { it.toDomain() }
                    ?: emptyList()
            },
            mapFromEntity = { it.toMovie() },
            forceRefresh = forceRefresh
        )
    }

    override suspend fun getMatchYourVibeMovies(
        genreId: Int,
        page: Int,
        forceRefresh: Boolean
    ): List<Movie> {
        return homeCacheHelper.getCachedOrFetchHomeItems(
            categoryType = CATEGORY_MATCHES_VIBE,
            fetchFromRemote = {
                movieRemoteDataSource.getMatchYourVibeMovies(
                    genreId,
                    page
                ).results?.map { it.toDomain() } ?: emptyList()
            },
            mapFromEntity = { it.toMovie() },
            forceRefresh = forceRefresh
        )
    }


    private companion object {
        const val CATEGORY_TRENDING = "TRENDING"
        const val CATEGORY_RECENTLY_RELEASED = "RECENTLY_RELEASED"
        const val CATEGORY_UPCOMING = "UPCOMING"
        const val CATEGORY_MATCHES_VIBE = "MATCHES_VIBE"
    }

    override suspend fun getMatchedMovies(
        page: Int,
        genres: String?,
        runtimeGte: Int?,
        runtimeLte: Int?,
        releaseDateGte: String?,
        releaseDateLte: String?
    ): List<Movie> {
        val movies = movieRemoteDataSource.getMatchedMovies(
            page,
            genres,
            runtimeGte,
            runtimeLte,
            releaseDateGte,
            releaseDateLte
        )
        return movies.results?.mapNotNull { runCatching { it.toDomain() }.getOrNull() } ?: emptyList()
    }
}
