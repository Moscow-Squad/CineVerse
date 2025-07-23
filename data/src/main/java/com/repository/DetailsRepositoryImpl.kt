package com.repository


import com.android.domain.model.CreditsDetails
import com.android.domain.model.details.ListOfSeries
import com.android.domain.model.details.MovieDetail
import com.android.domain.model.details.Season
import com.android.domain.model.details.SeriesDetail
import com.android.domain.repository.DetailsRepository
import com.data_source.local.DetailsLocalDataSource
import com.data_source.remote.DetailsRemoteDataSource
import com.mapper.toDomain
import com.remote.dto.review.RatingRequestDto

class DetailsRepositoryImpl(
    private val detailsRemoteDataSource: DetailsRemoteDataSource,
    private val detailsLocalDataSource: DetailsLocalDataSource,
) : DetailsRepository {
    override suspend fun getMoviesDetail(movieId: Int): MovieDetail {
        val res = detailsRemoteDataSource.getMovieDetails(movieId)
        res.genres?.forEach { detailsLocalDataSource.insertFavouriteGenre(it.id) }
        return res.toDomain()
    }

    override suspend fun getSeriesDetail(seriesId: Int): SeriesDetail {
        val res = detailsRemoteDataSource.getSeriesDetails(seriesId)
        res.genres.forEach { detailsLocalDataSource.insertFavouriteGenre(it.id) }
        return res.toDomain()
    }

    override suspend fun getCreditsDetails(id: Int): CreditsDetails {
        val response = detailsRemoteDataSource.getCredits(id)
        return response.toDomain()
    }

    override suspend fun getSeriesCreditsDetails(id: Int): CreditsDetails {
        val response = detailsRemoteDataSource.getSeriesCredits(id)
        return response.toDomain()
    }

    override suspend fun getRecommendations(id: Int,page:Int): List<Movie> {
        val response = detailsRemoteDataSource.getRecommendations(id,page)
        return response.map { it.toDomain() }
    }

    override suspend fun getLatestSeasons(): List<Season> {
        val response = detailsRemoteDataSource.getLatestSeasons()
        return response.map { it.toDomain() }
    }

    override suspend fun getListOfSeries(id: Int, page: Int): List<ListOfSeries> {
        val response = detailsRemoteDataSource.getListOfSeries(id, page)
        return listOf(response.toDomain())
    }

    override suspend fun getReviewsPage(id: Int, page: Int, isMovie: Boolean): List<Review> {
        val response = detailsRemoteDataSource.getReviews(id, page, isMovie)
        return response.results.orEmpty().mapNotNull { it?.toDomain() }
    }

    override suspend fun rateMovie(
        rating: Float,
        movieId: Int
    ) {
        detailsRemoteDataSource.rateMovie(
            rating = RatingRequestDto(value = rating),
            movieId = movieId
        )
    }

    override suspend fun rateSeries(
        rating: Float,
        seriesId: Int
    ) {
        detailsRemoteDataSource.rateSeries(
            rating = RatingRequestDto(value = rating),
            seriesId = seriesId
        )
    }

}