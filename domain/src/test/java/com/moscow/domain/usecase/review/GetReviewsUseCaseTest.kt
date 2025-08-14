package com.moscow.domain.usecase.review

import com.moscow.domain.model.Review
import com.moscow.domain.repository.MovieRepository
import com.moscow.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetReviewsUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var seriesRepository: SeriesRepository
    private lateinit var useCase: GetReviewsUseCase

    @BeforeEach
    fun setup() {
        movieRepository = mockk()
        seriesRepository = mockk()
        useCase = GetReviewsUseCase(movieRepository, seriesRepository)
    }

    @Test
    fun `invoke should return movie reviews when isMovie is true`() = runTest {
        val id = 1
        val page = 1
        val expectedReviews = listOf(
            Review(
                id = "r1",
                author = "Ahmed",
                username = "ahmed_user",
                avatarPath = "https://avatar.com/ahmed.jpg",
                rating = 4.5f,
                content = "Absolutely loved it!",
                createdAt = LocalDate(2025, 7, 25)
            )
        )

        coEvery { movieRepository.getReviewsMovie(id, page) } returns expectedReviews

        val result = useCase(id, page, isMovie = true)

        assertEquals(expectedReviews, result)
        coVerify(exactly = 1) { movieRepository.getReviewsMovie(id, page) }
    }

    @Test
    fun `invoke should return series reviews when isMovie is false`() = runTest {
        val id = 2
        val page = 1
        val expectedReviews = listOf(
            Review(
                id = "r2",
                author = "Ahmed",
                username = "ahmed_user",
                avatarPath = "https://avatar.com/ahmed.jpg",
                rating = 4.5f,
                content = "Absolutely loved it!",
                createdAt = LocalDate(2025, 7, 25)
            )
        )

        coEvery { seriesRepository.getSeriesReviews(id, page) } returns expectedReviews

        val result = useCase(id, page, isMovie = false)

        assertEquals(expectedReviews, result)
        coVerify(exactly = 1) { seriesRepository.getSeriesReviews(id, page) }
    }
}