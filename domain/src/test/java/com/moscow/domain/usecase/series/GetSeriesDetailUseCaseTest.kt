package com.moscow.domain.usecase.series

import com.moscow.domain.model.Series
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
class GetSeriesDetailUseCaseTest {

    private val repository: SeriesRepository = mockk()
    private lateinit var useCase: GetSeriesDetailUseCase

    @BeforeEach
    fun setup() {
        useCase = GetSeriesDetailUseCase(repository)
    }

    @Test
    fun `should fetch series detail by id`() = runTest {
        val expected = mockk<Series>()
        coEvery { repository.getSeriesDetail(5) } returns expected

        val result = useCase(5)

        assertEquals(expected, result)
        coVerify { repository.getSeriesDetail(5) }
    }
}