package com.moscow.remote.data_source

import com.moscow.data_source.remote.SeriesRemoteDataSource
import com.moscow.domain.repository.UserRepository
import com.moscow.remote.dto.details.MediaTrailersDto
import com.moscow.remote.dto.details.SeriesCreditDto
import com.moscow.remote.dto.rating.UserRatingResponse
import com.moscow.remote.dto.rating.series.RatedSeriesDto
import com.moscow.remote.dto.review.RatingRequestDto
import com.moscow.remote.dto.review.ReviewDto
import com.moscow.remote.dto.series.ListOfSeriesDto
import com.moscow.remote.dto.series.SeriesDetailDto
import com.moscow.remote.dto.series.SeriesDto
import com.moscow.remote.services.SeriesService
import com.moscow.utils.ApiResponse
import com.moscow.utils.handleApi
import javax.inject.Inject

class SeriesRemoteDataSourceImpl @Inject constructor(
    private val seriesService: SeriesService,
    private val userRepository: UserRepository
) : SeriesRemoteDataSource {

    override suspend fun getPopularSeries(page: Int): ApiResponse<SeriesDto> =
        handleApi {
            seriesService.getPopularSeries(page)
        }

    override suspend fun getSeriesDetails(id: Int): SeriesDetailDto =
        handleApi {
            seriesService.getSeriesDetails(id)
        }

    override suspend fun rateSeries(rating: RatingRequestDto, id: Int) {
        val sessionId = userRepository.getSessionId()
        handleApi {
            seriesService.rateSeries(
                id,
                sessionId,
                rating
            )
        }
    }

    override suspend fun deleteRatingSeries(seriesId: Int){
        val sessionId = userRepository.getSessionId()
        handleApi {
            seriesService.deleteRatingSeries(
                seriesId,
                sessionId
            )
        }
    }

    override suspend fun getRatedSeries(
        userId: Int,
        page: Int
    ): ApiResponse<RatedSeriesDto> {
        val sessionId = userRepository.getSessionId()
        return handleApi {
            seriesService.getRatedSeries(
                userId,
                sessionId,
                page
            )
        }
    }

    override suspend fun getUserRatingForSeries(seriesId: Int): UserRatingResponse {
        val sessionId = userRepository.getSessionId()
        return handleApi {
            seriesService.getUserRatingForSeries(
                seriesId,
                sessionId
            )
        }
    }

    override suspend fun getLatestSeasons(): SeriesDetailDto =
        handleApi {
            seriesService.getLatestSeasons()
        }

    override suspend fun getListOfSeries(id: Int, page: Int): ListOfSeriesDto =
        handleApi {
            seriesService.getListOfSeries(
                id,
                page
            )
        }

    override suspend fun getSeriesReviews(id: Int, page: Int): ApiResponse<ReviewDto> =
        handleApi {
            seriesService.getSeriesReviews(id, page)
        }

    override suspend fun getSeriesRecommendations(
        id: Int,
        page: Int
    ): ApiResponse<SeriesDto> =
        handleApi {
            seriesService.getSeriesRecommendations(id, page)
        }

    override suspend fun getSeriesByGenreId(genreId: Int, page: Int): ApiResponse<SeriesDto> =
        handleApi {
            seriesService.getSeriesByGenreId(genreId, page)
        }

    override suspend fun getSeriesCredits(seriesId: Int): SeriesCreditDto =
        handleApi {
            seriesService.getSeriesCredits(seriesId)
        }

    override suspend fun getSeriesTrailers(seriesId: Int): MediaTrailersDto =
        handleApi {
            seriesService.getSeriesTrailers(seriesId)
        }


    override suspend fun getTopRatedTVSeries(page: Int): ApiResponse<SeriesDto> =
        handleApi {
            seriesService.getTopRatedTVSeries(page)

        }
}