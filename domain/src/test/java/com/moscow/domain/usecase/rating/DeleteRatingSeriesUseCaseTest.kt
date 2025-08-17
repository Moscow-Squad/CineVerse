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
class DeleteRatingSeriesUseCaseTest {

    private val seriesRepository: SeriesRepository = mockk()
    private lateinit var useCase: DeleteRatingSeriesUseCase

    @BeforeEach
    fun setup() {
        useCase = DeleteRatingSeriesUseCase(seriesRepository)
    }

    @Test
    fun `invoke should call deleteRatingSeries with correct id`() = runTest {
        val seriesId = 42
        coEvery { seriesRepository.deleteRatingSeries(seriesId) } just Runs

        useCase(seriesId)

        coVerify(exactly = 1) { seriesRepository.deleteRatingSeries(seriesId) }
    }
}