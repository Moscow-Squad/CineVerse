package com.moscow.remote.data_source

import com.google.common.truth.Truth.assertThat
import com.moscow.data_source.remote.MovieRemoteDataSource
import com.moscow.domain.repository.auth.UserRepository
import com.moscow.remote.dto.media_item.common.CreditsDetailsDto
import com.moscow.remote.dto.media_item.common.MediaTrailersDto
import com.moscow.remote.dto.media_item.movie.MovieDetailDto
import com.moscow.remote.dto.media_item.movie.MovieDto
import com.moscow.remote.dto.rating.request.RatingRequestDto
import com.moscow.remote.dto.rating.response.RatedMovieDto
import com.moscow.remote.dto.rating.response.UserRatingResponse
import com.moscow.remote.dto.review.ReviewDto
import com.moscow.remote.services.MovieService
import com.moscow.utils.ApiResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

class MovieRemoteDataSourceImplTest {

    private lateinit var movieService: MovieService
    private lateinit var userRepository: UserRepository
    private lateinit var dataSource: MovieRemoteDataSource

    @BeforeEach
    fun setup() {
        movieService = mockk()
        userRepository = mockk()
        dataSource = MovieRemoteDataSourceImpl(movieService, userRepository)
    }

    @Test
    fun `getPopularMovies returns body on success`() = runTest {
        val page = 1
        val expected = ApiResponse(results = listOf(MovieDto(id = 1)))
        coEvery { movieService.getPopularMovies(page) } returns Response.success(expected)

        val result = dataSource.getPopularMovies(page)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { movieService.getPopularMovies(page) }
    }

    @Test
    fun `getMovieDetails returns body on success`() = runTest {
        val id = 10
        val expected = mockk<MovieDetailDto>()
        coEvery { movieService.getMovieDetails(id) } returns Response.success(expected)

        val result = dataSource.getMovieDetails(id)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { movieService.getMovieDetails(id) }
    }

    @Test
    fun `rateMovie returns Unit on success and calls with sessionId`() = runTest {
        val id = 5
        val rating = RatingRequestDto(value = 8.5f)
        val sessionId = "sid_rate"
        coEvery { userRepository.getSessionId() } returns sessionId
        coEvery { movieService.rateMovie(id, sessionId, rating) } returns Response.success(Unit)

        val result = dataSource.rateMovie(rating, id)

        assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) { userRepository.getSessionId() }
        coVerify(exactly = 1) { movieService.rateMovie(id, sessionId, rating) }
    }

    @Test
    fun `deleteRatingMovie returns Unit on success and calls with sessionId`() = runTest {
        val movieId = 77
        val sessionId = "sid_del"
        coEvery { userRepository.getSessionId() } returns sessionId
        coEvery {
            movieService.deleteRatingMovie(
                movieId,
                sessionId
            )
        } returns Response.success(Unit)

        val result = dataSource.deleteRatingMovie(movieId)

        assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) { userRepository.getSessionId() }
        coVerify(exactly = 1) { movieService.deleteRatingMovie(movieId, sessionId) }
    }

    @Test
    fun `getRatedMovies returns body on success and uses sessionId`() = runTest {
        val userId = 11
        val page = 2
        val sessionId = "sid_rated"
        val expected = ApiResponse(results = listOf(mockk<RatedMovieDto>()))
        coEvery { userRepository.getSessionId() } returns sessionId
        coEvery { movieService.getRatedMovies(userId, sessionId, page) } returns Response.success(
            expected
        )

        val result = dataSource.getRatedMovies(userId, page)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { userRepository.getSessionId() }
        coVerify(exactly = 1) { movieService.getRatedMovies(userId, sessionId, page) }
    }

    @Test
    fun `getUserRatingForMovie returns body on success and uses sessionId`() = runTest {
        val movieId = 9
        val sessionId = "sid_user_rating"
        val expected = mockk<UserRatingResponse>()
        coEvery { userRepository.getSessionId() } returns sessionId
        coEvery { movieService.getUserRatingForMovie(movieId, sessionId) } returns Response.success(
            expected
        )

        val result = dataSource.getUserRatingForMovie(movieId)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { userRepository.getSessionId() }
        coVerify(exactly = 1) { movieService.getUserRatingForMovie(movieId, sessionId) }
    }

    @Test
    fun `getMovieCredits returns body on success`() = runTest {
        val id = 3
        val expected = mockk<CreditsDetailsDto>()
        coEvery { movieService.getMovieCredits(id) } returns Response.success(expected)

        val result = dataSource.getMovieCredits(id)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { movieService.getMovieCredits(id) }
    }

    @Test
    fun `getMovieReviews returns body on success`() = runTest {
        val id = 4
        val page = 1
        val expected = ApiResponse(results = listOf(mockk<ReviewDto>()))
        coEvery { movieService.getMovieReviews(id, page) } returns Response.success(expected)

        val result = dataSource.getMovieReviews(id, page)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { movieService.getMovieReviews(id, page) }
    }

    @Test
    fun `getMoviesRecommendations returns body on success`() = runTest {
        val id = 1
        val page = 1
        val expected = ApiResponse(results = listOf(MovieDto(id = 2)))
        coEvery { movieService.getMoviesRecommendations(id, page) } returns Response.success(
            expected
        )

        val result = dataSource.getMoviesRecommendations(id, page)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { movieService.getMoviesRecommendations(id, page) }
    }

    @Test
    fun `getMoviesByGenreId returns body on success`() = runTest {
        val genreId = 28
        val page = 1
        val expected = ApiResponse(results = listOf(MovieDto(id = 7)))
        coEvery {
            movieService.getMoviesByGenreId(
                genreId,
                page
            )
        } returns Response.success(expected)

        val result = dataSource.getMoviesByGenreId(genreId, page)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { movieService.getMoviesByGenreId(genreId, page) }
    }

    @Test
    fun `getMovieTrailer returns body on success`() = runTest {
        val id = 123
        val expected = mockk<MediaTrailersDto>()
        coEvery { movieService.getMovieTrailers(id) } returns Response.success(expected)

        val result = dataSource.getMovieTrailer(id)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { movieService.getMovieTrailers(id) }
    }

    @Test
    fun `getTrendingMovies returns body on success`() = runTest {
        val expected = ApiResponse(results = listOf(MovieDto(id = 5)))
        coEvery { movieService.getTrendingMovies(any(), any()) } returns Response.success(expected)

        val result = dataSource.getTrendingMovies()

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { movieService.getTrendingMovies(any(), any()) }
    }

    @Test
    fun `getUpComingMovies returns body on success`() = runTest {
        val page = 3
        val expected = ApiResponse(results = listOf(MovieDto(id = 6)))
        coEvery { movieService.getUpComingMovies(page) } returns Response.success(expected)

        val result = dataSource.getUpComingMovies(page)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { movieService.getUpComingMovies(page) }
    }

    @Test
    fun `getRecentlyReleasedMovies returns body on success`() = runTest {
        val page = 1
        val expected = ApiResponse(results = listOf(MovieDto(id = 8)))
        coEvery { movieService.getRecentlyReleasedMovies(page) } returns Response.success(expected)

        val result = dataSource.getRecentlyReleasedMovies(page)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { movieService.getRecentlyReleasedMovies(page) }
    }

    @Test
    fun `getMatchYourVibeMovies returns body on success`() = runTest {
        val genreId = 35
        val page = 2
        val expected = ApiResponse(results = listOf(MovieDto(id = 9)))
        coEvery { movieService.getMatchYourVibeMovies(genreId, page) } returns Response.success(
            expected
        )

        val result = dataSource.getMatchYourVibeMovies(genreId, page)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { movieService.getMatchYourVibeMovies(genreId, page) }
    }

    @Test
    fun `getMatchedMovies returns success response`() = runTest {
        val page = 1
        val genres = "action"
        val runtimeGte = 90
        val runtimeLte = 120
        val releaseDateGte = "2020-01-01"
        val releaseDateLte = "2023-12-31"

        val expected = ApiResponse(results = listOf(MovieDto(id = 5)))
        coEvery {
            movieService.getMatchedMovies(
                page,
                genres,
                runtimeGte,
                runtimeLte,
                releaseDateGte,
                releaseDateLte
            )
        } returns Response.success(expected)

        val result = dataSource.getMatchedMovies(
            page,
            genres,
            runtimeGte,
            runtimeLte,
            releaseDateGte,
            releaseDateLte
        )

        assertThat(result).isEqualTo(expected)

        coVerify(exactly = 1) {
            movieService.getMatchedMovies(
                page,
                genres,
                runtimeGte,
                runtimeLte,
                releaseDateGte,
                releaseDateLte
            )
        }
    }
}