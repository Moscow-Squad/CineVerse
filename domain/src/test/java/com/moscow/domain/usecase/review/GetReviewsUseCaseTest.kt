package com.moscow.domain.usecase.review

import com.moscow.domain.model.Review
import com.moscow.domain.repository.MovieRepository
import com.moscow.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

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
        // Given
        val id = 1
        val page = 1
        val expectedReviews = listOf(
            Review(
                id = "r1",
                author = "Ahmed",
                username = "ahmed_user",
                avatarPath = "https://avatar.com/ahmed.jpg",
                rating = 4.5,
                content = "Absolutely loved it!",
                createdAt = null
            )
        )
        coEvery { movieRepository.getReviewsMovie(id, page) } returns expectedReviews

        // When
        val result = useCase(id, page, isMovie = true)

        // Then
        assertEquals(expectedReviews, result)
    }

    @Test
    fun `invoke should return series reviews when isMovie is false`() = runTest {
        // Given
        val id = 2
        val page = 1
        val expectedReviews = listOf(
            Review(
                id = "r1",
                author = "Ahmed",
                username = "ahmed_user",
                avatarPath = "https://avatar.com/ahmed.jpg",
                rating = 4.5,
                content = "Absolutely loved it!",
                createdAt = null
            )
        )
        coEvery { seriesRepository.getSeriesReviews(id, page) } returns expectedReviews

        // When
        val result = useCase(id, page, isMovie = false)

        // Then
        assertEquals(expectedReviews, result)
    }
}
