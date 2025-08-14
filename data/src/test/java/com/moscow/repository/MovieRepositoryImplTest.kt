package com.moscow.repository

import com.moscow.data_source.local.DetailsLocalDataSource
import com.moscow.data_source.remote.MovieRemoteDataSource
import com.moscow.domain.model.CreditsInfo
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Review
import com.moscow.domain.usecase.rating.GetRatedMoviesUseCase.RatedMovieResult
import com.moscow.local.entity.MediaItemEntity
import com.moscow.mapper.toDomain
import com.moscow.mapper.toMovie
import com.moscow.mapper.toOutputResult
import com.moscow.remote.dto.genre.GenreDto
import com.moscow.remote.dto.media_item.common.CreditsDetailsDto
import com.moscow.remote.dto.media_item.movie.MovieDto
import com.moscow.remote.dto.rating.request.RatingRequestDto
import com.moscow.remote.dto.rating.response.RatedMovieDto
import com.moscow.remote.dto.rating.response.UserRatingResponse
import com.moscow.remote.dto.review.ReviewDto
import com.moscow.utils.ApiResponse
import com.moscow.utils.HomeCacheHelper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals


class MovieRepositoryImplTest {

    private val homeCacheHelper = mockk<HomeCacheHelper>()
    private val movieRemoteDataSource = mockk<MovieRemoteDataSource>()
    private val detailLocalDataSource = mockk<DetailsLocalDataSource>()
    private lateinit var movieRepositoryImpl: MovieRepositoryImpl

    @BeforeEach
    fun setup() {
        movieRepositoryImpl = MovieRepositoryImpl(
            homeCacheHelper,
            movieRemoteDataSource,
            detailLocalDataSource
        )
    }

    @Test
    fun `addRatingMovie should call remote data source with correct rating`() = runTest {

        val movieId = 123
        val rating = 8.5f
        coEvery { movieRemoteDataSource.rateMovie(any(), any()) } returns Unit

        movieRepositoryImpl.addRatingMovie(movieId, rating)

        coVerify(exactly = 1) {
            movieRemoteDataSource.rateMovie(
                rating = match { it.value == 8.5f },
                id = movieId
            )
        }
    }

    @Test
    fun `deleteRatingMovie should call remote data source with correct movie id`() = runTest {

        val movieId = 123
        coEvery { movieRemoteDataSource.deleteRatingMovie(movieId) } returns Unit

        movieRepositoryImpl.deleteRatingMovie(movieId)

        coVerify(exactly = 1) { movieRemoteDataSource.deleteRatingMovie(123) }
    }


    @Test
    fun `getUserRatingMovie should return user rating from remote data source`() = runTest {

        val movieId = 123
        val mockUserRatingResponse = mockk<UserRatingResponse>()
        every { mockUserRatingResponse.userRating } returns 8
        coEvery { movieRemoteDataSource.getUserRatingForMovie(movieId) } returns mockUserRatingResponse

        val result = movieRepositoryImpl.getUserRatingMovie(movieId)

        assertEquals(8, result)
        coVerify(exactly = 1) { movieRemoteDataSource.getUserRatingForMovie(movieId) }
    }

    private fun createMockMovie(id: Int, title: String): Movie {
        return Movie(
            id = id,
            title = title,
            overview = "Test overview",
            trailerUrl = "trailer.mp4",
            backdropUrl = "backdrop.jpg",
            posterUrl = "poster.jpg",
            releaseDate = LocalDate(2023, 1, 1),
            genreIds = listOf(1, 2),
            genres = listOf("Action", "Comedy"),
            duration = Movie.Duration(2, 30),
            rating = 8.5f
        )
    }
}