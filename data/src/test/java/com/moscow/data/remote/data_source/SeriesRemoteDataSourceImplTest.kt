package com.moscow.data.remote.data_source

import com.google.common.truth.Truth.assertThat
import com.moscow.data_source.remote.SeriesRemoteDataSource
import com.moscow.domain.repository.auth.UserRepository
import com.moscow.remote.data_source.SeriesRemoteDataSourceImpl
import com.moscow.remote.dto.media_item.common.CreditsDetailsDto
import com.moscow.remote.dto.media_item.common.MediaTrailersDto
import com.moscow.remote.dto.media_item.series.SeriesDetailDto
import com.moscow.remote.dto.media_item.series.SeriesDto
import com.moscow.remote.dto.rating.request.RatingRequestDto
import com.moscow.remote.dto.rating.response.RatedSeriesDto
import com.moscow.remote.dto.rating.response.UserRatingResponse
import com.moscow.remote.dto.review.ReviewDto
import com.moscow.remote.services.SeriesService
import com.moscow.utils.ApiResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

class SeriesRemoteDataSourceImplTest {

    private lateinit var seriesService: SeriesService
    private lateinit var userRepository: UserRepository
    private lateinit var dataSource: SeriesRemoteDataSource

    @BeforeEach
    fun setup() {
        seriesService = mockk()
        userRepository = mockk()
        dataSource = SeriesRemoteDataSourceImpl(seriesService, userRepository)
    }

    @Test
    fun `getPopularSeries returns body on success`() = runTest {
        val page = 1
        val expected = ApiResponse(results = listOf(SeriesDto(id = 1)))
        coEvery { seriesService.getPopularSeries(page) } returns Response.success(expected)

        val result = dataSource.getPopularSeries(page)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { seriesService.getPopularSeries(page) }
    }

    @Test
    fun `getSeriesDetails returns body on success`() = runTest {
        val id = 10
        val expected = mockk<SeriesDetailDto>()
        coEvery { seriesService.getSeriesDetails(id) } returns Response.success(expected)

        val result = dataSource.getSeriesDetails(id)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { seriesService.getSeriesDetails(id) }
    }

    @Test
    fun `rateSeries returns Unit on success and calls with sessionId`() = runTest {
        val id = 5
        val rating = RatingRequestDto(value = 9.0f)
        val sessionId = "sid_rate_series"
        coEvery { userRepository.getSessionId() } returns sessionId
        coEvery { seriesService.rateSeries(id, sessionId, rating) } returns Response.success(Unit)

        val result = dataSource.rateSeries(rating, id)

        assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) { userRepository.getSessionId() }
        coVerify(exactly = 1) { seriesService.rateSeries(id, sessionId, rating) }
    }

    @Test
    fun `deleteRatingSeries returns Unit on success and calls with sessionId`() = runTest {
        val seriesId = 77
        val sessionId = "sid_del_series"
        coEvery { userRepository.getSessionId() } returns sessionId
        coEvery { seriesService.deleteRatingSeries(seriesId, sessionId) } returns Response.success(Unit)

        val result = dataSource.deleteRatingSeries(seriesId)

        assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) { userRepository.getSessionId() }
        coVerify(exactly = 1) { seriesService.deleteRatingSeries(seriesId, sessionId) }
    }

    @Test
    fun `getRatedSeries returns body on success and uses sessionId`() = runTest {
        val userId = 11
        val page = 2
        val sessionId = "sid_rated_series"
        val expected = ApiResponse(results = listOf(mockk<RatedSeriesDto>()))
        coEvery { userRepository.getSessionId() } returns sessionId
        coEvery { seriesService.getRatedSeries(userId, sessionId, page) } returns Response.success(expected)

        val result = dataSource.getRatedSeries(userId, page)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { userRepository.getSessionId() }
        coVerify(exactly = 1) { seriesService.getRatedSeries(userId, sessionId, page) }
    }

    @Test
    fun `getUserRatingForSeries returns body on success and uses sessionId`() = runTest {
        val seriesId = 9
        val sessionId = "sid_user_rating_series"
        val expected = mockk<UserRatingResponse>()
        coEvery { userRepository.getSessionId() } returns sessionId
        coEvery { seriesService.getUserRatingForSeries(seriesId, sessionId) } returns Response.success(expected)

        val result = dataSource.getUserRatingForSeries(seriesId)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { userRepository.getSessionId() }
        coVerify(exactly = 1) { seriesService.getUserRatingForSeries(seriesId, sessionId) }
    }

    @Test
    fun `getLatestSeasons returns body on success`() = runTest {
        val expected = mockk<SeriesDetailDto>()
        coEvery { seriesService.getLatestSeasons() } returns Response.success(expected)

        val result = dataSource.getLatestSeasons()

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { seriesService.getLatestSeasons() }
    }

    @Test
    fun `getListOfSeries returns body on success`() = runTest {
        val id = 22
        val page = 3
        val expected = ApiResponse(results = listOf(SeriesDto(id = 2)))
        coEvery { seriesService.getListOfSeries(id, page) } returns Response.success(expected)

        val result = dataSource.getListOfSeries(id, page)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { seriesService.getListOfSeries(id, page) }
    }

    @Test
    fun `getSeriesReviews returns body on success`() = runTest {
        val id = 4
        val page = 1
        val expected = ApiResponse(results = listOf(mockk<ReviewDto>()))
        coEvery { seriesService.getSeriesReviews(id, page) } returns Response.success(expected)

        val result = dataSource.getSeriesReviews(id, page)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { seriesService.getSeriesReviews(id, page) }
    }

    @Test
    fun `getSeriesRecommendations returns body on success`() = runTest {
        val id = 1
        val page = 1
        val expected = ApiResponse(results = listOf(SeriesDto(id = 7)))
        coEvery { seriesService.getSeriesRecommendations(id, page) } returns Response.success(expected)

        val result = dataSource.getSeriesRecommendations(id, page)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { seriesService.getSeriesRecommendations(id, page) }
    }

    @Test
    fun `getSeriesByGenreId returns body on success`() = runTest {
        val genreId = 28
        val page = 1
        val expected = ApiResponse(results = listOf(SeriesDto(id = 5)))
        coEvery { seriesService.getSeriesByGenreId(genreId, page) } returns Response.success(expected)

        val result = dataSource.getSeriesByGenreId(genreId, page)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { seriesService.getSeriesByGenreId(genreId, page) }
    }

    @Test
    fun `getSeriesCredits returns body on success`() = runTest {
        val seriesId = 3
        val expected = mockk<CreditsDetailsDto>()
        coEvery { seriesService.getSeriesCredits(seriesId) } returns Response.success(expected)

        val result = dataSource.getSeriesCredits(seriesId)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { seriesService.getSeriesCredits(seriesId) }
    }

    @Test
    fun `getSeriesTrailers returns body on success`() = runTest {
        val seriesId = 123
        val expected = mockk<MediaTrailersDto>()
        coEvery { seriesService.getSeriesTrailers(seriesId) } returns Response.success(expected)

        val result = dataSource.getSeriesTrailers(seriesId)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { seriesService.getSeriesTrailers(seriesId) }
    }

    @Test
    fun `getTopRatedTVSeries returns body on success`() = runTest {
        val page = 2
        val expected = ApiResponse(results = listOf(SeriesDto(id = 99)))
        coEvery { seriesService.getTopRatedTVSeries(page) } returns Response.success(expected)

        val result = dataSource.getTopRatedTVSeries(page)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { seriesService.getTopRatedTVSeries(page) }
    }
}