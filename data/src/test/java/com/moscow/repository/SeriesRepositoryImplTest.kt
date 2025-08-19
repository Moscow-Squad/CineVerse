package com.moscow.repository

import com.google.common.truth.Truth.assertThat
import com.moscow.data_source.local.DetailsLocalDataSource
import com.moscow.data_source.remote.SeriesRemoteDataSource
import com.moscow.domain.repository.SeriesRepository
import com.moscow.remote.dto.media_item.series.SeriesDto
import com.moscow.remote.dto.rating.request.RatingRequestDto
import com.moscow.remote.dto.rating.response.RatedSeriesDto
import com.moscow.remote.dto.rating.response.UserRatingResponse
import com.moscow.remote.dto.review.ReviewDto
import com.moscow.utils.ApiResponse
import com.moscow.utils.HomeCacheHelper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SeriesRepositoryImplTest {

    private lateinit var homeCacheHelper: HomeCacheHelper
    private lateinit var remote: SeriesRemoteDataSource
    private lateinit var detailsLocal: DetailsLocalDataSource
    private lateinit var repository: SeriesRepository

    @BeforeEach
    fun setup() {
        homeCacheHelper = mockk()
        remote = mockk()
        detailsLocal = mockk(relaxed = true)
        repository = SeriesRepositoryImpl(homeCacheHelper, remote, detailsLocal)
    }

    @Test
    fun `getPopularSeries returns empty list when remote results are null`() = runTest {
        val page = 1
        coEvery { remote.getPopularSeries(page) } returns ApiResponse<SeriesDto>(results = null)

        val result = repository.getPopularSeries(page)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { remote.getPopularSeries(page) }
    }

    @Test
    fun `rateSeries calls remote with correct id and rating`() = runTest {
        val id = 55
        val rating = RatingRequestDto(8.5f)

        coEvery { remote.rateSeries(id = id, rating = rating) } returns Unit

        repository.rateSeries(id = id, rating = 8.5f)

        coVerify(exactly = 1) { remote.rateSeries(id = id, rating = rating) }
    }

    @Test
    fun `deleteRatingSeries forwards call to remote`() = runTest {
        val seriesId = 99
        coEvery { remote.deleteRatingSeries(seriesId) } returns Unit

        repository.deleteRatingSeries(seriesId)

        coVerify(exactly = 1) { remote.deleteRatingSeries(seriesId) }
    }

    @Test
    fun `getRatedSeries returns empty when remote results are null`() = runTest {
        val userId = 7
        val page = 2
        coEvery {
            remote.getRatedSeries(
                userId,
                page
            )
        } returns ApiResponse<RatedSeriesDto>(results = null)

        val result = repository.getRatedSeries(userId, page)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { remote.getRatedSeries(userId, page) }
    }

    @Test
    fun `getUserRatingForSeries returns 0 when user rating is null`() = runTest {
        val seriesId = 12
        coEvery { remote.getUserRatingForSeries(seriesId) } returns
                UserRatingResponse(userRating = null, favorite = null, id = null, watchlist = null)

        val result = repository.getUserRatingForSeries(seriesId)

        assertThat(result).isEqualTo(0)
        coVerify(exactly = 1) { remote.getUserRatingForSeries(seriesId) }
    }

    @Test
    fun `getListOfSeries returns empty when remote results are null`() = runTest {
        val id = 1
        val page = 3
        coEvery { remote.getListOfSeries(id, page) } returns ApiResponse<SeriesDto>(results = null)

        val result = repository.getListOfSeries(id, page)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { remote.getListOfSeries(id, page) }
    }

    @Test
    fun `getSeriesRecommendations returns empty when remote results are null`() = runTest {
        val id = 4
        val page = 1
        coEvery {
            remote.getSeriesRecommendations(
                id,
                page
            )
        } returns ApiResponse<SeriesDto>(results = null)

        val result = repository.getSeriesRecommendations(id, page)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { remote.getSeriesRecommendations(id, page) }
    }

    @Test
    fun `getSeriesByGenreId returns empty when remote results are null`() = runTest {
        val genre = 28
        val page = 2
        coEvery {
            remote.getSeriesByGenreId(
                genre,
                page
            )
        } returns ApiResponse<SeriesDto>(results = null)

        val result = repository.getSeriesByGenreId(genre, page)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { remote.getSeriesByGenreId(genre, page) }
    }

    @Test
    fun `getSeriesReviews returns empty when remote results are null`() = runTest {
        val id = 88
        val page = 1
        coEvery { remote.getSeriesReviews(id, page) } returns ApiResponse<ReviewDto>(results = null)

        val result = repository.getSeriesReviews(id, page)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { remote.getSeriesReviews(id, page) }
    }
}