package com.remote.source

import com.remote.dto.CreditsDetailsDto
import com.remote.dto.series.ListOfSeriesDto
import com.remote.dto.details.MovieDetailDto
import com.remote.dto.series.SeriesDetailDto
import com.remote.dto.review.RatingRequestDto
import com.remote.services.DetailsService
import com.utils.handleApi

class DetailsRemoteDataSource(
    private val detailsService: DetailsService
) {
    suspend fun getMovieDetails(id: Int): MovieDetailDto = handleApi {
        detailsService.getMovieDetails(id)
    }

    suspend fun getSeriesDetails(id: Int): SeriesDetailDto = handleApi {
        detailsService.getSeriesDetails(id)
    }

    suspend fun getCredits(id:Int): CreditsDetailsDto = handleApi {
        detailsService.getCredits(id)
    }

    suspend fun rateMovie(rating: RatingRequestDto, movieId: Int) {
        handleApi {
            detailsService.rateMovie(
                movieId,
                "31044f799b3ccf5e970b994ca0022ef8865c1e35",
                rating
            )
        }
    }

    suspend fun rateSeries(rating: RatingRequestDto, seriesId: Int) = handleApi {
        detailsService.rateSeries(
            seriesId,
            "31044f799b3ccf5e970b994ca0022ef8865c1e35",
            rating
        )
    }

    suspend fun getLatestSeasons(): SeriesDetailDto = handleApi {
        detailsService.getLatestSeasons()
    }

    suspend fun getListOfSeries(id: Int, page: Int): ListOfSeriesDto = handleApi {
        detailsService.getListOfSeries(
            id,
            page
        )
    }

}