package com.moscow.domain.usecase.recently_viewed

import com.moscow.domain.repository.HistoryTipsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetShowHistoryTipUseCaseTest {

    private lateinit var historyTipsRepository: HistoryTipsRepository
    private lateinit var useCase: GetShowHistoryTipUseCase

    @BeforeEach
    fun setup() {
        historyTipsRepository = mockk()
        useCase = GetShowHistoryTipUseCase(historyTipsRepository)
    }

    @Test
    fun `invoke should return showHistoryTip result`() = runTest {
        val expectedResult = true

        coEvery { historyTipsRepository.showHistoryTip() } returns expectedResult

        val result = useCase()

        assertEquals(expectedResult, result)
        coVerify(exactly = 1) { historyTipsRepository.showHistoryTip() }
    }
}