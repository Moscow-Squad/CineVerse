package com.remote.data_source

import com.data_source.remote.DetailsRemoteDataSource
import com.remote.dto.CreditsDetailsDto
import com.remote.dto.details.MovieDetailDto
import com.remote.dto.details.SeriesCreditDto
import com.remote.dto.review.RatingRequestDto
import com.remote.dto.series.ListOfSeriesDto
import com.remote.dto.series.SeriesDetailDto
import com.remote.services.DetailsService
import com.utils.handleApi

class DetailsRemoteDataSourceImpl(
    private val detailsService: DetailsService
) : DetailsRemoteDataSource {
    override suspend fun getMovieDetails(id: Int): MovieDetailDto = handleApi {
        detailsService.getMovieDetails(id)
    }

    override suspend fun getSeriesDetails(id: Int): SeriesDetailDto = handleApi {
        detailsService.getSeriesDetails(id)
    }

    override suspend fun getCredits(id: Int): CreditsDetailsDto = handleApi {
        detailsService.getCredits(id)
    }

    override suspend fun rateMovie(rating: RatingRequestDto, movieId: Int) =
        handleApi {
            detailsService.rateMovie(
                movieId,
                "31044f799b3ccf5e970b994ca0022ef8865c1e35",
                rating
            )
        }

    override suspend fun rateSeries(rating: RatingRequestDto, seriesId: Int) = handleApi {
        detailsService.rateSeries(
            seriesId,
            "31044f799b3ccf5e970b994ca0022ef8865c1e35",
            rating
        )
    }

    override suspend fun getLatestSeasons(): SeriesDetailDto = handleApi {
        detailsService.getLatestSeasons()
    }

    override suspend fun getListOfSeries(id: Int, page: Int): ListOfSeriesDto = handleApi {
        detailsService.getListOfSeries(
            id,
            page
        )
    }

    override suspend fun getSeriesCredits(seriesId: Int): SeriesCreditDto = handleApi {
        detailsService.getSeriesCredits(seriesId)
    }
}