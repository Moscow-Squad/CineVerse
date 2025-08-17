package com.moscow.domain.usecase.rating

import com.moscow.domain.repository.SeriesRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RateSeriesUseCaseTest {

    private val seriesRepository: SeriesRepository = mockk()
    private lateinit var useCase: RateSeriesUseCase

    @BeforeEach
    fun setup() {
        useCase = RateSeriesUseCase(seriesRepository)
    }

    @Test
    fun `rateSeriesUse should call repository with correct rating and seriesId`() = runTest {
        val rating = 4.5f
        val seriesId = 123
        coEvery { seriesRepository.rateSeries(id = seriesId, rating = rating) } just Runs

        useCase.rateSeriesUse(rating, seriesId)

        coVerify { seriesRepository.rateSeries(id = seriesId, rating = rating) }
    }
}