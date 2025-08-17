package com.moscow.domain.usecase.rating

import com.moscow.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserRatingForSeriesUseCaseTest {

    private val seriesRepository: SeriesRepository = mockk()
    private lateinit var useCase: GetUserRatingForSeriesUseCase

    @BeforeEach
    fun setup() {
        useCase = GetUserRatingForSeriesUseCase(seriesRepository)
    }

    @Test
    fun `invoke should return user rating for series`() = runTest {
        val seriesId = 789
        val expectedRating = 41245
        coEvery { seriesRepository.getUserRatingForSeries(seriesId) } returns expectedRating

        val result = useCase(seriesId)

        assertEquals(expectedRating, result)
        coVerify { seriesRepository.getUserRatingForSeries(seriesId) }
    }
}