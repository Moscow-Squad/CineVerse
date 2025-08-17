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
class GetTopRatedTVShowsUseCaseTest {

    private val repository: SeriesRepository = mockk()
    private lateinit var useCase: GetTopRatedTVShowsUseCase

    @BeforeEach
    fun setup() {
        useCase = GetTopRatedTVShowsUseCase(repository)
    }

    @Test
    fun `should fetch top rated TV series`() = runTest {
        val expected = listOf(mockk<Series>())
        coEvery { repository.getTopRatedTVSeries(1, false) } returns expected

        val result = useCase(1)

        assertEquals(expected, result)
        coVerify { repository.getTopRatedTVSeries(1, false) }
    }
}