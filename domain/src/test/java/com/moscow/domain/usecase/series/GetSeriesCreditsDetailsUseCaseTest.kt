package com.moscow.domain.usecase.series

import com.moscow.domain.model.CreditsInfo
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
class GetSeriesCreditsDetailsUseCaseTest {

    private val repository: SeriesRepository = mockk()
    private lateinit var useCase: GetSeriesCreditsDetailsUseCase

    @BeforeEach
    fun setup() {
        useCase = GetSeriesCreditsDetailsUseCase(repository)
    }

    @Test
    fun `should fetch series credits details`() = runTest {
        val expected = mockk<CreditsInfo>()
        coEvery { repository.getSeriesCreditsDetails(7) } returns expected

        val result = useCase(7)

        assertEquals(expected, result)
        coVerify { repository.getSeriesCreditsDetails(7) }
    }
}