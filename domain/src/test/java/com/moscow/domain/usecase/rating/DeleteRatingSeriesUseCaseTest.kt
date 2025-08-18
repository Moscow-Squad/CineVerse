package com.moscow.domain.usecase.rating

import com.moscow.domain.repository.SeriesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteRatingSeriesUseCaseTest {

    private lateinit var seriesRepository: SeriesRepository
    private lateinit var useCase: DeleteRatingSeriesUseCase

    @BeforeEach
    fun setup() {
        seriesRepository = mockk(relaxed = true)
        useCase = DeleteRatingSeriesUseCase(seriesRepository)
    }

    @Test
    fun `invoke should call deleteRatingSeries with correct id`() = runTest {
        val seriesId = 42

        useCase(seriesId)

        coVerify { seriesRepository.deleteRatingSeries(seriesId) }
    }
}